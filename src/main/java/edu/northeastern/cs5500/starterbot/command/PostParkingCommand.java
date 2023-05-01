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
 * Class that handles the continuation of the post command in a Discord bot,
 * will initiate after PostSquareFeetCommand response, a String Select Interaction.
 * Stores response in PostSublet instance and then
 */
@Singleton
@Slf4j
public class PostParkingCommand implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "Is there parking at the property?";
    private static final String PARK = "6. Parking available: ";
    private static final Boolean F = false;
    /*
     * Empty, dagger injection
     */
    @Inject
    public PostParkingCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command, a String.
     */
    @Override
    @Nonnull
    public String getName() {
        return "postparkingspaceavailability";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Post parking space availability");
    }

    /*
     * Handles slash command intearction events for the command, not viewable by user
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /postparkingspaceavailability");
    }

    /*
     * Handles String select interaction events for the previous command.
     * Receives input from the user via string select from Square feet command
     * and stores the responses in the PostSublet instance.
     * Sends the next question to the user.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        PostSublet sublet = PostSublet.getInstance();
        Integer sizeHouse = Integer.valueOf(event.getInteraction().getValues().get(0));
        Objects.requireNonNull(sizeHouse);
        // Saving response to singleton, saving response to embed
        sublet.getSubletListing().getProperty().setSquareFeet(sizeHouse);
        sublet.addField(
                "Square Feet: ",
                sublet.getSubletListing().getProperty().getSquareFeet().toString(),
                F);
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField(PARK, QUERY, F);
        StringSelectMenu menu =
                StringSelectMenu.create("postpetavailability")
                        .setPlaceholder("Parking available: Yes/No")
                        .addOption("Yes", "yes")
                        .addOption("No", "no")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
