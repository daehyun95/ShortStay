package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/*
 * Interface containing the methods for a ButtonHandler.
 */
public interface ButtonHandler {
    /*
     * Returns the command name, a String.
     */
    @Nonnull
    public String getName();

    /*
     * Responds to the button interaction, void.
     */
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event);
}
