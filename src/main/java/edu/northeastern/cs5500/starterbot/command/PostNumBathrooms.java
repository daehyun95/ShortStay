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
 * Singleton class that will add the number of bedrooms to the embed builder instance in PostSublet.
 * Then will query the user for the number of bathrooms in their property.
 */
@Singleton
@Slf4j
public class PostNumBathrooms implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "How many bathrooms are there in the property?";
    private static final String BATHS = "4. Number of bathrooms ";
    private static final Boolean F = false;

    /*
     * Empty, dagger injection
     */
    @Inject
    public PostNumBathrooms() {
        // Defined public and empty for Dagger injection
    }
    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "postnumbathrooms";
    }
    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Post number of Bathrooms");
    }

    /*
     * Handles slash command intearction events for the command, not viewable by user
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /postnumbathrooms");
    }
    /*
     * Handles string select interaction events for the command.
     * Receives input from the user via string selected and stores the responses in the PostSublet instance.
     * Sends the next question to the user.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        PostSublet sublet = PostSublet.getInstance();
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
                StringSelectMenu.create("postsquarefeet")
                        .setPlaceholder("number of bathrooms")
                        .addOption("1", "1")
                        .addOption("2", "2")
                        .addOption("3", "3")
                        .addOption("4+", "4")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
