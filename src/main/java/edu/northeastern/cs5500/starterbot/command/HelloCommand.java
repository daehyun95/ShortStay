package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/* Singleton hello command class that contains the information
 * pertaining to a /hello command. ShortStayBot will introduce itself to
 * the user.
 */
@Singleton
@Slf4j
public class HelloCommand implements SlashCommandHandler {

    /*
     * Empty, dagger injection
     */
    @Inject
    public HelloCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "hello";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Say hello to ShortStayBot");
    }

    /*
     * Handles slash command intearction events for the command
     * Says hello to the user with brief instructions on how to proceed.
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /hello");
        event.reply(
                        "Hello, my name is ShortStayBot!\n"
                                + "I am here to assist you in finding short-term housing from another student,\n"
                                + "or to help you post short-term housing for other students to find. \n"
                                + "You can search for housing by entering /find. \n"
                                + "Or you can post housing by entering /post. \n"
                                + "Or you can delete a post by entering /delete.")
                .queue();
    }
}
