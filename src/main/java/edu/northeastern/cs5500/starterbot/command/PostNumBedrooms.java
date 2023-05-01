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
 * A Singleton class that contains the information to query a user for the number
 * of bedrooms in their property
 */
@Singleton
@Slf4j
public class PostNumBedrooms implements SlashCommandHandler, StringSelectHandler {
    private static final String BEDS = "3. Number of bedrooms";
    private static final String QUERY = "How many bedrooms are there in the property?";
    private static final Boolean F = false;

    /*
     * Empty, dagger injection
     */
    @Inject
    public PostNumBedrooms() {
        // Defined public and empty for Dagger injection
    }
    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "postnumbedrooms";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Post number of bedrooms");
    }

    /*
     * Handles slash command intearction events for the command, not viewable by user
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /postnumbedrooms");
    }

    /*
     * Handles string select interaction events for the command.
     * Receives input from the user via string selected and stores the responses in the PostSublet instance.
     * Sends the next question to the user.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        PostSublet sublet = PostSublet.getInstance();
        // Retrieving user selection
        Integer price = Integer.valueOf(event.getInteraction().getValues().get(0));
        Objects.requireNonNull(price);
        sublet.getSubletListing().getProperty().setPrice(price);

        // Adding response to the embed builder
        sublet.addField(
                "Monthly cost: ",
                sublet.getSubletListing().getProperty().getPrice().toString(),
                false);

        // Getting a clone of the embed instance for query reply
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        // Next query
        tempEmbed.addField(BEDS, QUERY, F);
        StringSelectMenu menu =
                StringSelectMenu.create("postnumbathrooms")
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
