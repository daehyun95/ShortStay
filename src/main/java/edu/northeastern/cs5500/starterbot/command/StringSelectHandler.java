package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

/*
 * Public interface, slashcommandhandler
 * Contains methods necessary for implementing SlashCOmmandHandler.
 */
public interface StringSelectHandler {
    /*
     * Method that returns command name, a String
     */
    @Nonnull
    public String getName();

    /*
     * Function that is called on a string select interaction, void.
     */
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event);
}
