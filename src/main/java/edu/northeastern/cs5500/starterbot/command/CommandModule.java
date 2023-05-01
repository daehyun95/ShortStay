package edu.northeastern.cs5500.starterbot.command;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/*
 * Module class containing the commands and their required handlers.
 */
@Module
public class CommandModule {

    /*
     * Slash command handler for preferrednamecommand.
     */
    @Provides
    @IntoSet
    public SlashCommandHandler providePreferredNameCommand(
            PreferredNameCommand preferredNameCommand) {
        return preferredNameCommand;
    }

    /*
     * Slash command handler for hellocommand, a command that sends a hello message
     * with instructions.
     */
    @Provides
    @IntoSet
    public SlashCommandHandler provideHelloCommand(HelloCommand helloCommand) {
        return helloCommand;
    }
    /*
     * Slash command handler for restart command, a command that will reset post and find sublets.
     */
    @Provides
    @IntoSet
    public SlashCommandHandler provideRestartCommand(RestartCommand restartCommand) {
        return restartCommand;
    }
    /*
     * Slash command handler for find command, a command to find a listing
     */
    @Provides
    @IntoSet
    public SlashCommandHandler provideFindCommand(FindCommand findCommand) {
        return findCommand;
    }

    /*
     * Button command handler for findcommand.
     */
    @Provides
    @IntoSet
    public ButtonHandler provideFindCommandHandler(FindCommand findCommand) {
        return findCommand;
    }

    /*
     * Sltring select handler for FindNumBedrooms command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler provideFindNumBedroomsdMenuHandler(FindNumBedrooms findNumBedrooms) {
        return findNumBedrooms;
    }

    /*
     * Sltring select handler for FindNumBathrooms command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler provideFindNumBathroomsHandlerdMenuHandler(
            FindNumBathrooms findNumBathrooms) {
        return findNumBathrooms;
    }

    /*
     * String Select handler for FindSquareFeet command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler provideFindSquareFeetCommandHandlerdMenuHandler(
            FindSquareFeetCommand findSquareFeetCommand) {
        return findSquareFeetCommand;
    }

    /*
     * String select handler for FindParking command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler provideFindParkingCommandHandlerdMenuHandler(
            FindParkingCommand findParkingCommand) {
        return findParkingCommand;
    }

    /*
     * String select handler for FindPet command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler provideFindPetCommandHandlerdMenuHandler(
            FindPetCommand findPetCommand) {
        return findPetCommand;
    }

    /*
     * String Select handler for FindFurnished command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler provideFindFurnishedCommandHandlerdMenuHandler(
            FindFurnishedCommand findFurnishedCommand) {
        return findFurnishedCommand;
    }

    /*
     * String Select handler for FindZipCode command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler provideFindZipCodeCommandHandlerMenuHandler(
            FindZipCodeCommand findZipCodeCommand) {
        return findZipCodeCommand;
    }

    /*
     * Slash command handler for Post command, start of commands that will
     * start a PostSublet to post a listing.
     */
    @Provides
    @IntoSet
    public SlashCommandHandler providePostCommand(PostCommand postCommand) {
        return postCommand;
    }
    /*
     * Button handler for Post command.
     */
    @Provides
    @IntoSet
    public ButtonHandler providePostCommandHandler(PostCommand postCommand) {
        return postCommand;
    }

    /*
     * StringSelect handler for PostNumBedrooms command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler providePostNumBedroomsMenuHandler(PostNumBedrooms postNumBedrooms) {
        return postNumBedrooms;
    }

    /*
     * StringSelect handler for PostNumBathrooms command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler providePostNumBathroomsHandlerMenuHandler(
            PostNumBathrooms postNumBathrooms) {
        return postNumBathrooms;
    }

    /*
     * StringSelect handler for PostSquareFeet command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler providePostSquareFeetCommandHandlerMenuHandler(
            PostSquareFeetCommand postSquareFeetCommand) {
        return postSquareFeetCommand;
    }

    /*
     * StringSelect handler for PostParking command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler providePostParkingCommandHandlerMenuHandler(
            PostParkingCommand postParkingCommand) {
        return postParkingCommand;
    }

    /*
     * String Select handler for PostPet command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler providePostPetCommandHandlerMenuHandler(
            PostPetCommand postPetCommand) {
        return postPetCommand;
    }

    /*
     * String Select handler for PostFurnished command.
     */
    @Provides
    @IntoSet
    public StringSelectHandler providePostFurnishedCommandHandlerMenuHandler(
            PostFurnishedCommand postFurnishedCommand) {
        return postFurnishedCommand;
    }
    /*
     * String select handler for PostFinalDetails command.
     * Modal interaction to find zipcode, start/end dates, email
     * and phone number.
     */
    @Provides
    @IntoSet
    public StringSelectHandler providePostFinalDetailsCommandHandlerMenuHandler(
            PostFinalDetailsCommand postFinalDetailsCommand) {
        return postFinalDetailsCommand;
    }

    @Provides
    @IntoSet
    public SlashCommandHandler provideDeleteCommand(DeleteCommand deleteCommand) {
        return deleteCommand;
    }
}
