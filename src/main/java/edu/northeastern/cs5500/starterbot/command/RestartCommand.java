package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.model.*;
import edu.northeastern.cs5500.starterbot.model.FindSublet;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/*
 * Contains the information for the restart command, a slash command, that
 * when called will reset any instances of the FindSublet singleton or the
 * PostSublet singleton.
 */
@Singleton
@Slf4j
public class RestartCommand implements SlashCommandHandler {
    /*
     * Empty, dagger injection
     */
    @Inject
    public RestartCommand() {
        // Defined public and empty for Dagger injection
    }
    /*
     * Returns the name of the command, a String.
     */
    @Override
    @Nonnull
    public String getName() {
        return "restart";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Restart. Removes all saved post or find information.");
    }

    /*
     * On slash command retrieves instance of each class, then will call the restart
     * methods in each class to reset.
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /restart");

        // Ensure is reset when this command is called
        PostSublet subletPost = PostSublet.getInstance();
        FindSublet subletFind = FindSublet.getInstance();
        subletPost.reset();
        subletFind.reset();

        event.reply("All find/post information has been reset. Enter /find or /post to continue.")
                .queue();
    }
}
