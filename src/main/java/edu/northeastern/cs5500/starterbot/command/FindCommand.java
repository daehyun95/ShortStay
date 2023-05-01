package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.model.*;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

/*
 * Class that handles the find command in a Discord bot implemting the SlashCommandHandler and Button Handler
 * Instantiates a new FindSublet instance to initiate a search for a sublet.
 * When command is called, it will query the user for sublet infromation and
 * store the responses into the FindSublet instance
 */
@Singleton
@Slf4j
public class FindCommand implements SlashCommandHandler, ButtonHandler {
    private static final String QUERY = "What type of housing are you looking for?";
    private static final String HTYPE = "1. House Type:";
    private static final Boolean F = false;

    @Inject
    public FindCommand() {
        // Defined public and empty for Dagger injection
    }

    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "find";
    }

    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "find sublet");
    }

    /*
     * Instantiates what is necessary to find a sublet
     */
    public void newFindSublet() {
        FindSublet sublet = FindSublet.getInstance();
        // Setting up FindSublet
        Address newAddress = new Address();
        Property newProperty = new Property();
        newProperty.setAddress(newAddress);
        sublet.getSubletListing().setProperty(newProperty);
    }

    /*
     * Handles slash command intearction events for the command
     * Queries the user for sublet information and stores the responses in the FindSublet instance.
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /find");
        // Ensure is reset when this command is called
        FindSublet sublet = FindSublet.getInstance();
        sublet.reset();

        // Setting up a FindSublet
        newFindSublet();

        // Begin querying the user for sublet information
        // Fx from FindSublet will return a copy
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField(HTYPE, QUERY, F);

        event.replyEmbeds(tempEmbed.build())
                .setActionRow(
                        Button.secondary(this.getName() + ":CONDO", "CONDO"),
                        Button.secondary(this.getName() + ":APARTMENT", "APARTMENT"),
                        Button.secondary(this.getName() + ":HOUSE", "HOUSE"))
                .queue();
    }

    /*
     * Handles button interaction events for the command.
     * Receives input from the user via buttons and stores the responses in the FindSublet instance.
     * Sents the next question to the user
     */
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        FindSublet sublet = FindSublet.getInstance();
        // Receive information from user button selection
        String houseType = event.getButton().getLabel();

        // Setting the FindSublet listing housingtype based on user button selection
        Property.HousingType houseTypeEnum = Property.HousingType.valueOf(houseType);
        sublet.getSubletListing().getProperty().setHousingType(houseTypeEnum);
        String type = sublet.getSubletListing().getProperty().getHousingType().toString();

        // Adding the information received to embedbuilder instance
        sublet.addField("House type:", type, false);

        // Clone of findSublet embed builder
        EmbedBuilder tempEmbed = sublet.returnTempEmbed();
        tempEmbed.addField("2. Cost:", "Select the value", F);
        StringSelectMenu menu =
                StringSelectMenu.create("findnumbedrooms")
                        .setPlaceholder("Enter monthly cost")
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
                        .build();
        event.replyEmbeds(tempEmbed.build()).setEphemeral(true).addActionRow(menu).queue();
    }
}
