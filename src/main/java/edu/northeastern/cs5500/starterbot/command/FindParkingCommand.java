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
 * Class handles inteartction with users to find parking space availability for a sublet
 * Implements SlashCommandHandler and StringSelectHandler
 */
@Singleton
@Slf4j
public class FindParkingCommand implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "Do you need parking space?";
    private static final String PARK = "6. Parking Space availability ";
    private static final Boolean F = false;

    @Inject
    public FindParkingCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "findparkingspaceavailability";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Find parking space availability");
    }

    /*
     * Handles the slash command interaction event
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /findparkingspaceavailability");
    }

    /*
     * Handles the string select interaction event and
     * updates the parking space availability for the sublet
     * based on the selected option in the select menu
     * and sents the next question to the user
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        FindSublet sublet = FindSublet.getInstance();
        Integer sizeHouse = Integer.valueOf(event.getInteraction().getValues().get(0));
        Objects.requireNonNull(sizeHouse);

        // Saving response to singleton
        sublet.getSubletListing().getProperty().setSquareFeet(sizeHouse);

        // Adding response to the embed builder
        sublet.addField(
                "Square Feet: ",
                sublet.getSubletListing().getProperty().getSquareFeet().toString(),
                F);

        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField(PARK, QUERY, F);
        StringSelectMenu menu =
                StringSelectMenu.create("findpetavailability")
                        .setPlaceholder("Yes/No")
                        .addOption("Yes", "yes")
                        .addOption("No", "no")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
