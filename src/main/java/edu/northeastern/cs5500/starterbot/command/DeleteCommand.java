package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.controller.SubletListingController;
import edu.northeastern.cs5500.starterbot.model.SubletListing;
import edu.northeastern.cs5500.starterbot.repository.MongoDBRepository;
import edu.northeastern.cs5500.starterbot.service.MongoDBService;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bson.types.ObjectId;

/* Class that handles the delete command in a Discord bot implementing the SlashCommandHandler
 * Instantiates a new DeleteCommand instance to initiate a deletion of a sublet.
 * When command is called, it will query the user for sublet id and
 * delete the sublet with the given id
 */
@Singleton
@Slf4j
public class DeleteCommand implements SlashCommandHandler {
    /*
     * Returns the name of the command
     */
    @Inject
    public DeleteCommand() {
        // Defined public and empty for Dagger injection
    }
    /*
     * Returns the name of the command
     */
    @Override
    @Nonnull
    public String getName() {
        return "delete";
    }
    /*
     * Returns a CommandData object that contains information about the command
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "for deleting a sublet")
                .addOption(OptionType.STRING, "subletid", "Please enter your sublet id", true);
    }
    /*
     * deals with the interaction of the command
     * if the id is valid, delete the sublet with the given id
     * if the id is invalid, respond with an error message
     * if the id is not in the database, respond with an error message
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /delete");
        var option = event.getOption("subletid"); // retreive id value
        if (option == null) {
            log.error("Received null value for mandatory parameter 'subletid'");
            return;
        }

        // delete the id in the database
        MongoDBService service = new MongoDBService();
        MongoDBRepository<SubletListing> repo =
                new MongoDBRepository<>(SubletListing.class, service);
        SubletListingController controller = new SubletListingController(repo);

        ObjectId mongoId = null;
        try {
            mongoId = new ObjectId(option.getAsString());
        } catch (Exception e) { // catch invalid id
            log.error("Invalid id");
            event.reply("This is an invalid ID number.").queue();
            return;
        }

        if (controller.getListing(mongoId) != null) {
            controller.deleteListing(mongoId);
            event.reply("Deletion succeeded.").queue(); // only here the deletion succeeds
        } else { // catch id not in database
            log.error("This ID does not exist in the database");
            event.deferReply().queue();
            event.getHook().sendMessage("This ID does not exist in the database.").queue();
        }
    }
}
