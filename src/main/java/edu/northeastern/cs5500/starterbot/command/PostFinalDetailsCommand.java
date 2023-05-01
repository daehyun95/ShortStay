package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.model.*;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

/*
 * Singleton java class that contains the information for a /postFinalDetailsCommand command
 * Will create a modal (form inputs) to attain the address (city, state, zipcode)
 * Final user information (school email, phone number)
 * Finally, will get the start and end dates for the listing.
 */
@Singleton
@Slf4j
public class PostFinalDetailsCommand implements SlashCommandHandler, StringSelectHandler {

    /*
     * Empty, dagger injection
     */
    @Inject
    public PostFinalDetailsCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "postfinaldetails";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Post final details for Listing");
    }

    /*
     * Handles slash command intearction events for the command, not viewable by user
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /postfinaldetails");
    }

    /*
     * String select intearction event from postcommand response
     * Queries the user for sublet information and stores the responses in the PostSublet instance.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        PostSublet sublet = PostSublet.getInstance();
        String furnishedResponse = event.getInteraction().getValues().get(0);
        Objects.requireNonNull(furnishedResponse);
        Boolean isFurnished;

        // Changing string response to a boolean
        if (event.getValues().get(0).equalsIgnoreCase("Yes")) {
            isFurnished = true;
        } else {
            isFurnished = false;
        }
        // Saving information
        sublet.getSubletListing().getProperty().setIsFurnished(isFurnished);
        sublet.addField("Is Furnished: ", furnishedResponse, false);

        TextInput zipcodeInput = buildZipcodeInput();
        TextInput emailInput = buildEmailInput();
        TextInput phoneInput = buildPhoneInput();
        TextInput startDateInput = buildStartDateInput();
        TextInput endDateInput = buildEndDateInput();

        Modal modal =
                Modal.create("postfinaldetails", "details")
                        .addActionRows(
                                ActionRow.of(zipcodeInput),
                                ActionRow.of(emailInput),
                                ActionRow.of(phoneInput),
                                ActionRow.of(startDateInput),
                                ActionRow.of(endDateInput))
                        .build();

        event.replyModal(modal).queue();
    }

    /*
     * Function that will return a text input for the zipcode
     * To be added to the modal as an action row
     */
    private TextInput buildZipcodeInput() {
        return TextInput.create("zipcode", "Zip Code", TextInputStyle.SHORT)
                .setPlaceholder("Enter property zipcode")
                .setMinLength(5)
                .setMaxLength(5)
                .build();
    }

    /*
     * Function that will return a text input for the users school email
     * To be added to the modal as an action row
     */
    private TextInput buildEmailInput() {
        return TextInput.create("email", "Email", TextInputStyle.SHORT)
                .setPlaceholder("Enter your school email (must end in .edu)")
                .build();
    }

    /*
     * Function that will return a text input for the users phone number
     * To be added to the modal as an action row
     */
    private TextInput buildPhoneInput() {
        return TextInput.create("phone", "Phone", TextInputStyle.SHORT)
                .setPlaceholder("Enter your phone number (10 digits, no dashes)")
                .setMinLength(10)
                .setMaxLength(10)
                .build();
    }

    /*
     * Function that will return a text input for the users listing start date
     * To be added to the modal as an action row
     */
    private TextInput buildStartDateInput() {
        return TextInput.create("startDate", "Start Date", TextInputStyle.SHORT)
                .setPlaceholder("Enter start date exactly as: YYYY-MM-DD.")
                .setMinLength(10)
                .setMaxLength(10)
                .build();
    }

    /*
     * Function that will return a text input for the users listing end date
     * To be added to the modal as an action row
     */
    private TextInput buildEndDateInput() {
        return TextInput.create("endDate", "End Date", TextInputStyle.SHORT)
                .setPlaceholder("Enter end date exactly as: YYYY-MM-DD)")
                .build();
    }
}
