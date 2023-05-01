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
 * Class handles inteartction with users to find the number of bedroom required for a sublet
 * Implements the SlashCommandHandler and StringSelectHandler
 */
@Singleton
@Slf4j
public class FindNumBedrooms implements SlashCommandHandler, StringSelectHandler {
    private static final String BEDS = "3. Number of bedrooms";
    private static final String QUERY = "How many bedrooms are you looking for?";
    private static final Boolean F = false;

    @Inject
    public FindNumBedrooms() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "findnumbedrooms";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Find number of bedrooms");
    }

    /*
     * Handles the slash command interaction event
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /findnumbedrooms");
    }

    /*
     * Handles the string select interaction event and
     * updates the number of bedrooms for the sublet
     * based on the selected option in the select menu
     * and sents the next question to the user
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        FindSublet sublet = FindSublet.getInstance();
        // Retrieving user selection
        Integer price = Integer.valueOf(event.getInteraction().getValues().get(0));
        Objects.requireNonNull(price);
        sublet.getSubletListing().getProperty().setPrice(price);

        // Adding response to the embed builder
        sublet.addField(
                "Monthly maximum cost: ",
                sublet.getSubletListing().getProperty().getPrice().toString(),
                false);

        // Getting a clone of the embed instance
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();

        // Next query
        tempEmbed.addField(BEDS, QUERY, F);

        StringSelectMenu menu =
                StringSelectMenu.create("findnumbathrooms")
                        .setPlaceholder("number of bedrooms")
                        .addOption("0", "0")
                        .addOption("1", "1")
                        .addOption("2", "2")
                        .addOption("3", "3")
                        .addOption("4+", "4")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
