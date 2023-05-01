package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/*
 * Public interface, slashcommandhandler
 * Contains methods necessary for implementing SlashCOmmandHandler.
 */
public interface SlashCommandHandler {
    /*
     * Method that returns command name, a String
     */
    @Nonnull
    public String getName();

    /*
     * Returns commanddata of slash command, CommandData.
     */
    @Nonnull
    public CommandData getCommandData();

    /*
     * Function that is called on a slash command, void.
     */
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event);
}
