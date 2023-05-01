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

/*
 * Singleton class containing the information to send a modal to the user for 2 text inputs:
 * State and Zipcode.
 * User clicks submit --> will call onModalEvent in the Listener class
 */
@Singleton
@Slf4j
public class FindZipCodeCommand implements SlashCommandHandler, StringSelectHandler {
    @Inject
    public FindZipCodeCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "findzipcode";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Find zipcode");
    }

    /*
     * Handles slash interaction event, not viewable by user
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /findzipcode");
    }

    /*
     * Method called when the findfurnished command is called.
     * Will log information from the findfurnished command, save it to findsublet instance
     * and will respond with a modal interaction, a form for zipcode, startdate, and enddate.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        FindSublet sublet = FindSublet.getInstance();
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
        TextInput startDateInput = buildStartDateInput();
        TextInput endDateInput = buildEndDateInput();

        net.dv8tion.jda.api.interactions.modals.Modal modal =
                net.dv8tion.jda.api.interactions.modals.Modal.create("findzipcode", "zipcode")
                        .addActionRows(
                                ActionRow.of(zipcodeInput),
                                ActionRow.of(startDateInput),
                                ActionRow.of(endDateInput))
                        .build();

        event.replyModal(modal).queue();
    }

    /*
     * Method that will build a TextInput for the zipcode to add to a Modal.
     * Input requires a length of 5.
     */
    private TextInput buildZipcodeInput() {
        return TextInput.create("zipcode", "Zip Code", TextInputStyle.SHORT)
                .setPlaceholder("Enter property zipcode")
                .setMinLength(5)
                .setMaxLength(5)
                .build();
    }

    /*
     * Method that will build a TextInput for the start date to add to a Modal.
     * Input requires a length of 10.
     */
    private TextInput buildStartDateInput() {
        return TextInput.create("startDate", "Start Date", TextInputStyle.SHORT)
                .setPlaceholder("Enter start date exactly as: YYYY-MM-DD.")
                .setMinLength(10)
                .setMaxLength(10)
                .build();
    }

    /*
     * Method that will build a TextInput for the end date to add to a Modal.
     * Input requires a length of 10.
     */
    private TextInput buildEndDateInput() {
        return TextInput.create("endDate", "End Date", TextInputStyle.SHORT)
                .setPlaceholder("Enter end date exactly as: YYYY-MM-DD)")
                .setMinLength(10)
                .setMaxLength(10)
                .build();
    }
}
