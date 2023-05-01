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
public class PostSquareFeetCommand implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "About how many square feet is the property?";
    private static final String SIZE = "5. Size of house: ";
    private static final Boolean F = false;

    /*
     * Empty, dagger injection
     */
    @Inject
    public PostSquareFeetCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command, a String.
     */
    @Override
    @Nonnull
    public String getName() {
        return "postsquarefeet";
    }

    /*
     * Returns a CommandData object that contains information about the command, commandData.
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Post size of house");
    }
    /*
     * Handles slash command intearction events for the command, not viewable by user
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /postsquarefeet");
    }

    /*
     * Handles String select interaction events for the previous command.
     * Receives input from the user via string select from postnumbathrooms command
     * and stores the responses in the PostSublet instance.
     * Sends the next question to the user.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        PostSublet sublet = PostSublet.getInstance();
        // Retrieving information on the property's number of bathrooms
        Integer numbathrooms = Integer.valueOf(event.getInteraction().getValues().get(0));
        Objects.requireNonNull(numbathrooms);
        sublet.getSubletListing().getProperty().setBathrooms(numbathrooms);

        // Adding response to the embed builder instance
        sublet.addField(
                "Number of bathrooms: ",
                sublet.getSubletListing().getProperty().getBathrooms().toString(),
                F);

        // Next query to embed copy
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField(SIZE, QUERY, F);
        StringSelectMenu menu =
                StringSelectMenu.create("postparkingspaceavailability")
                        .setPlaceholder("Select estimated square feet of property")
                        .addOption("300", "300")
                        .addOption("400", "400")
                        .addOption("500", "500")
                        .addOption("600", "600")
                        .addOption("700", "700")
                        .addOption("800", "800")
                        .addOption("900", "900")
                        .addOption("1000", "1000")
                        .addOption("1100", "1100")
                        .addOption("1200", "1200")
                        .addOption("1300", "1300")
                        .addOption("1400", "1400")
                        .addOption("1500", "1500")
                        .addOption("1600", "1600")
                        .addOption("1700", "1700")
                        .addOption("1800+", "1800")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
