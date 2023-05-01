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
 * will initiate after parkingavailable response, a String Select Interaction.
 * Stores response in PostSublet instance and then
 */
@Singleton
@Slf4j
public class PostPetCommand implements SlashCommandHandler, StringSelectHandler {
    private static final String QUERY = "Are pets allowed at the property?";
    private static final String PETS = "7. Pet availability";
    private static final Boolean F = false;

    /*
     * Injectable pet command, empty for dagger injection
     */
    @Inject
    public PostPetCommand() {
        // Defined empty for dagger injection
    }

    /*
     * Getter for the name of the command, a String.
     */
    @Override
    @Nonnull
    public String getName() {
        return "postpetavailability";
    }

    /*
     * Getter for the command data, a CommandData.
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Post pet availability");
    }

    /*
     * Handles slash command intearction events for the command, not viewable by user
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /postpetavailability");
    }

    /*
     * Handles String select interaction events for the previous command.
     * Receives input from the user via string select from parking available command
     * and stores the responses in the PostSublet instance.
     * Sends the next question to the user.
     */
    @Override
    public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        PostSublet sublet = PostSublet.getInstance();
        String parkingResponse = event.getInteraction().getValues().get(0);
        Objects.requireNonNull(parkingResponse);
        Boolean parkingAvailable;

        // Changing string response to a boolean
        if (event.getValues().get(0).equalsIgnoreCase("Yes")) {
            parkingAvailable = true;
        } else {
            parkingAvailable = false;
        }
        // Saving response to postsublet instance
        sublet.getSubletListing().getProperty().setHasParking(parkingAvailable);
        sublet.addField("Parking available: ", parkingResponse, false);

        // Next question to user
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField(PETS, QUERY, F);
        StringSelectMenu menu =
                StringSelectMenu.create("postisfurnished")
                        .setPlaceholder("Yes/No")
                        .addOption("Yes", "yes")
                        .addOption("No", "no")
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
