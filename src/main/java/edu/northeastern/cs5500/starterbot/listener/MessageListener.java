package edu.northeastern.cs5500.starterbot.listener;

import edu.northeastern.cs5500.starterbot.command.ButtonHandler;
import edu.northeastern.cs5500.starterbot.command.SlashCommandHandler;
import edu.northeastern.cs5500.starterbot.command.StringSelectHandler;
import edu.northeastern.cs5500.starterbot.controller.*;
import edu.northeastern.cs5500.starterbot.model.*;
import edu.northeastern.cs5500.starterbot.repository.*;
import edu.northeastern.cs5500.starterbot.service.*;
import edu.northeastern.cs5500.starterbot.service.MongoDBService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * Class that extends ListenerAdapater. It used for handling interactions with messaging platform.
 * The class has fields for sets of SlashCommandHandler, ButtonHandler and StringSelectHandler
 * objects
 */
@Slf4j
public class MessageListener extends ListenerAdapter {
    private static final Integer EXPECTED_FIND_VALS = 3;
    private static final MessageListenerMethods listenerMethods = new MessageListenerMethods();

    @Inject Set<SlashCommandHandler> commands;
    @Inject Set<ButtonHandler> buttons;
    @Inject Set<StringSelectHandler> stringSelects;

    @Inject
    public MessageListener() {
        super();
    }

    /*
     * It is called when a user issues a slash command, and it searchs for the appropriate
     * SlashCommandHandler object to handle the coomand
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        for (SlashCommandHandler command : commands) {
            if (command.getName().equals(event.getName())) {
                command.onSlashCommandInteraction(event);
                return;
            }
        }
    }

    /*
     * It returns a collection of CommandData objects
     * representing all the slash commands that can be handled by MessageListener
     */
    public @Nonnull Collection<CommandData> allCommandData() {
        Collection<CommandData> commandData =
                commands.stream()
                        .map(SlashCommandHandler::getCommandData)
                        .collect(Collectors.toList());
        if (commandData == null) {
            return new ArrayList<>();
        }
        return commandData;
    }

    /*
     * It is called when a user interacts with a button and it searchs for the appropriate
     * ButtonHandler object to handle the interaction
     */
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        log.info("onButtonInteraction: {}", event.getButton().getId());
        String id = event.getButton().getId();
        Objects.requireNonNull(id);
        String handlerName = id.split(":", 2)[0];

        for (ButtonHandler buttonHandler : buttons) {
            if (buttonHandler.getName().equals(handlerName)) {
                buttonHandler.onButtonInteraction(event);
                return;
            }
        }

        log.error("Unknown button handler: {}", handlerName);
    }

    /*
     * It is called when a user interacts with a string select component,
     * and it searches for the appropriate StringSelectHandler object to handle the interaction
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        log.info("onStringSelectInteraction: {}", event.getComponent().getId());
        String handlerName = event.getComponent().getId();

        for (StringSelectHandler stringSelectHandler : stringSelects) {
            if (stringSelectHandler.getName().equals(handlerName)) {
                stringSelectHandler.onStringSelectInteraction(event);
                return;
            }
        }

        log.error("Unknown button handler: {}", handlerName);
    }

    /*
     * Receives the final inputs from the user, all text inputs from a form
     * function will call handleFindModalInteraction if the user's form
     * only requires zipcode, start, end date.
     */
    @Override
    public void onModalInteraction(@Nonnull ModalInteractionEvent event) {
        // Checks the size of the form that was filled out
        int numValues = event.getValues().size();
        if (numValues > EXPECTED_FIND_VALS) {
            retrievePostInformation(event);
        } else {
            retrieveFindInformation(event);
        }
    }

    /*
     * Receives the final inputs from the user for post command
     * First step is to retrieve values, then validate the inputs, then
     * will either display an error message or a success message with the listing ID
     * using an embed reply.
     */
    private void retrievePostInformation(@Nonnull ModalInteractionEvent event) {
        PostSublet sublet = PostSublet.getInstance();

        String zipcode = event.getValue("zipcode").getAsString();
        String email = event.getValue("email").getAsString();
        String phone = event.getValue("phone").getAsString();

        String startDate = event.getValue("startDate").getAsString();
        String endDate = event.getValue("endDate").getAsString();

        // Validates inputs, an error will return a message, a successful validation returns null
        String errorMessage =
                listenerMethods.validatePostInput(zipcode, email, phone, startDate, endDate);

        // basic embed base to be used by invalid or valid inputs
        EmbedBuilder embed =
                sublet.returnTempEmbed()
                        .addField("Zip Code: ", zipcode, false)
                        .addField("Email: ", email, false)
                        .addField("Phone: ", phone, false)
                        .addField("Start Date: ", startDate.toString(), false)
                        .addField("End Date: ", endDate.toString(), false);

        if (errorMessage != null) { // Invalid input
            embed.addField("Error: ", errorMessage, false);
            event.replyEmbeds(embed.build()).queue();
        } else {
            // If successful, will add find attributes to findsublet singleton instance
            Users newUsers = new Users();
            newUsers.setStudentEmail(email);
            newUsers.setPhoneNumber(phone);
            sublet.getSubletListing().setUser(newUsers);

            sublet.getSubletListing().getProperty().getAddress().setZipCode(zipcode);

            // Converts input YYYY-MM-DD to Date() in UTC timezone for mongoDB
            try {
                Date start = listenerMethods.convertDateStringToMongoDate(startDate);
                Date end = listenerMethods.convertDateStringToMongoDate(endDate);
                sublet.getSubletListing().setStartDate(start);
                sublet.getSubletListing().setEndDate(end);

                // Instantiate controller to add to MongoDB
                SubletListingController controller =
                        new SubletListingController(
                                new MongoDBRepository<SubletListing>(
                                        SubletListing.class, new MongoDBService()));
                // Posts new listing to the database
                SubletListing newListing = controller.addListing(sublet.getSubletListing());
                sublet.getSubletListing().setId(newListing.getId());

                embed.addField("Listing ID (Please save ID)", newListing.getId().toString(), false);
                event.replyEmbeds(embed.build()).queue();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /*
     * Receives the final inputs from the user for find command
     * First step is to retrieve values, then validate the inputs, if successful will
     * call findListing in controller to retrieve and display a valid subletListing.
     */
    private void retrieveFindInformation(@Nonnull ModalInteractionEvent event) {
        FindSublet sublet = FindSublet.getInstance();

        String zipcode = event.getValue("zipcode").getAsString();
        String startDate = event.getValue("startDate").getAsString();
        String endDate = event.getValue("endDate").getAsString();

        // Embed base to be used by invalid or validated inputs
        EmbedBuilder embed =
                sublet.returnTempEmbed()
                        .addField("Zip Code: ", zipcode, false)
                        .addField("Start Date: ", startDate, false)
                        .addField("End Date: ", endDate, false);

        // Validates inputs, null for success, error message for invalid inputs
        String errorMessage = listenerMethods.validateFindInput(zipcode, startDate, endDate);
        if (errorMessage != null) {
            embed.addField("Error: ", errorMessage, false);
            event.replyEmbeds(embed.build()).queue();
        } else {
            // Set valid inputs to FindSublet singleton attributes
            sublet.getSubletListing().getProperty().getAddress().setZipCode(zipcode);

            // Convert ISO Date string to Date retrieved from MongoDB
            try {
                Date start = listenerMethods.convertDateStringToMongoDate(startDate);
                Date end = listenerMethods.convertDateStringToMongoDate(endDate);
                sublet.getSubletListing().setStartDate(start);
                sublet.getSubletListing().setEndDate(end);

                SubletListingController controller =
                        new SubletListingController(
                                new MongoDBRepository<SubletListing>(
                                        SubletListing.class, new MongoDBService()));

                Collection<SubletListing> myCollection = controller.getAllListings();
                ArrayList<SubletListing> collectionList =
                        new ArrayList<>(myCollection); // Convert the collection to an ArrayList

                EmbedBuilder resultEmbed =
                        sublet.getSubletListing().returnClosestMatch(collectionList);
                event.replyEmbeds(resultEmbed.build()).queue();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
