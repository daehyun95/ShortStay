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
 * Class represents a handler for finding the number of bathroom required for a sublet
 * Implements the SlashCommandHandler and StringSelectHandler
 */
@Singleton
@Slf4j
public class FindNumBathrooms implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "How many bathrooms do you need?";
    private static final String BATHS = "4. Number of bathrooms ";
    private static final Boolean F = false;

    @Inject
    public FindNumBathrooms() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "findnumbathrooms";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Find number of Bathrooms");
    }

    /*
     * Handles the slash command interaction event
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /findnumbathrooms");
    }

    /*
     * Handles the string select interaction event and
     * updates the number of bathroom for the sublet
     * based on the selected option in the select menu
     * and sents the next question to the user
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        FindSublet sublet = FindSublet.getInstance();
        Integer numbedrooms = Integer.valueOf(event.getInteraction().getValues().get(0));
        Objects.requireNonNull(numbedrooms);
        sublet.getSubletListing().getProperty().setBedrooms(numbedrooms);
        sublet.addField(
                "Number of bedrooms: ",
                sublet.getSubletListing().getProperty().getBedrooms().toString(),
                false);

        EmbedBuilder tempEmbed = sublet.returnTempEmbed();

        tempEmbed.addField(BATHS, QUERY, F);

        StringSelectMenu menu =
                StringSelectMenu.create("findsquarefeet")
                        .setPlaceholder("number of bathrooms")
                        .addOption("1", "1")
                        .addOption("2", "2")
                        .addOption("3", "3")
                        .addOption("4+", "4")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
