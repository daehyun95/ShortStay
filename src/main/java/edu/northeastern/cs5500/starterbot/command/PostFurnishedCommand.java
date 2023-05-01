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
 * Singleton class for a furnished slash command, will query the user for information about
 * if the property is furnished. Will also store user's response to previous question
 * (PostPetCommand)
 */
@Singleton
@Slf4j
public class PostFurnishedCommand implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "Is the property furnished?";
    private static final String FURNISHED = "8. Is property furnished?";
    private static final Boolean F = false;

    /*
     * Empty, dagger injection
     */
    @Inject
    public PostFurnishedCommand() {
        // Defined public and empty for Dagger injection
    }
    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "postisfurnished";
    }
    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Is furnished?");
    }
    /*
     * Handles slash command intearction events for the command, not viewable by user
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /postisfurnished");
    }

    /*
     * Handles string select interaction events for the command.
     * Receives input from the user via string selected and stores the responses in the PostSublet instance.
     * Sends the next question to the user.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        PostSublet sublet = PostSublet.getInstance();
        String petResponse = event.getInteraction().getValues().get(0);
        Objects.requireNonNull(petResponse);
        Boolean petsOK;

        // Changing string response to a boolean
        if (event.getValues().get(0).equalsIgnoreCase("Yes")) {
            petsOK = true;
        } else if (event.getValues().get(0).equalsIgnoreCase("No")) {
            petsOK = false;
        } else {
            // In case there is an issue, will default to false
            petsOK = false;
        }

        // Setting boolean to property instance, keep string for embed
        sublet.getSubletListing().getProperty().setPetsOK(petsOK);
        sublet.addField("Pets allowed: ", petResponse, false);

        // Temporary embed will query user for zipcode
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField(FURNISHED, QUERY, F);
        StringSelectMenu menu =
                StringSelectMenu.create("postfinaldetails")
                        .setPlaceholder("Yes/No")
                        .addOption("Yes", "yes")
                        .addOption("No", "no")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
