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
 * Singleton class that contains the information for a findfurnished command.
 *
 * Will store petsOK user response for /find, then replies with a string select menu
 * Yes/No for user to respond if pets are okay.
 */
@Singleton
@Slf4j
public class FindFurnishedCommand implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "Do you need the property to be furnished?";
    private static final String FURNISHED = "8. Require furnished?";
    private static final Boolean F = false;

    /*
     * Public, empty method for FindFurnishedCOmmand dagger injection.
     */
    @Inject
    public FindFurnishedCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Method that will return the command name as a string, findisfurnished.
     */
    @Override
    @Nonnull
    public String getName() {
        return "findisfurnished";
    }

    /*
     * Returns command data, the name of the command and information.
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Need furnished?");
    }

    /*
     * Slash command interaction for the class.
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /findisfurnished");
    }

    /*
     * Returns String select menu to see if user needs the sublet to be furnished
     * Stores user's response to findpetcommand
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        FindSublet sublet = FindSublet.getInstance();
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

        // Setting boolean to property instance, but setting the response to the embed builder
        // for clarity to the user
        sublet.getSubletListing().getProperty().setPetsOK(petsOK);
        sublet.addField("Pets allowed: ", petResponse, false);

        // Temporary embed will query user for zipcode
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField(FURNISHED, QUERY, F);
        StringSelectMenu menu =
                StringSelectMenu.create("findzipcode")
                        .setPlaceholder("Yes/No")
                        .addOption("Yes", "yes")
                        .addOption("No", "no")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
