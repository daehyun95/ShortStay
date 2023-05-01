package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.model.*;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

/*
 * Class handles inteartction with users to find pet availability for sublet properties.
 * Implements the SlashCommandHandler and StringSelectHandler
 */
@Singleton
@Slf4j
public class FindPetCommand implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "Do you have pet?";
    private static final String PETS = "7. Pet availability";
    private static final Boolean F = false;

    @Inject
    public FindPetCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "findpetavailability";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Find pet availability");
    }

    /*
     * Handles the slash command interaction event
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /findpetavailability");
    }

    /*
     * Handles the string select interaction and
     * saves the user's response (whether they have a pet or not) and
     * and sents the next question to the user.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        FindSublet sublet = FindSublet.getInstance();
        String parkingResponse = event.getInteraction().getValues().get(0);
        Objects.requireNonNull(parkingResponse);
        Boolean parkingAvailable;

        // Changing string response to a boolean
        if (event.getValues().get(0).equalsIgnoreCase("Yes")) {
            parkingAvailable = true;
        } else if (event.getValues().get(0).equalsIgnoreCase("No")) {
            parkingAvailable = false;
        } else {
            // default to false in case of error for now
            parkingAvailable = false;
        }

        // Saving response to postsublet instance
        sublet.getSubletListing().getProperty().setHasParking(parkingAvailable);
        sublet.addField("Parking available: ", parkingResponse, false);

        // Next question to user
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField(PETS, QUERY, F);
        StringSelectMenu menu =
                StringSelectMenu.create("findisfurnished")
                        .setPlaceholder("Yes/No")
                        .addOption("Yes", "yes")
                        .addOption("No", "no")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
