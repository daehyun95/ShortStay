package edu.northeastern.cs5500.starterbot;

import dagger.Component;
import edu.northeastern.cs5500.starterbot.command.CommandModule;
import edu.northeastern.cs5500.starterbot.listener.MessageListener;
import edu.northeastern.cs5500.starterbot.repository.RepositoryModule;
import java.util.Collection;
import java.util.EnumSet;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

/*
 * Singleton Bot module, the public interface containing the application bot.
 */
@Component(modules = {CommandModule.class, RepositoryModule.class})
@Singleton
interface BotComponent {
    public Bot bot();
}
/*
 * Singleton Bot module, contains bot connection information and JDA connection.
 */
public class Bot {

    /*
     * Singleton Bot class, contains bot connection information and JDA connection.
     */
    @Inject
    Bot() {}

    @Inject MessageListener messageListener;

    /*
     * Getter for the bot token, a String.
     */
    static String getBotToken() {
        return new ProcessBuilder().environment().get("BOT_TOKEN");
    }

    /*
     * Start function, will be called on application start. Requires bot token, intents, JDA connection
     * to run application.
     */
    void start() {
        String token = getBotToken();
        if (token == null) {
            throw new IllegalArgumentException(
                    "The BOT_TOKEN environment variable is not defined.");
        }

        @SuppressWarnings("null")
        @Nonnull
        Collection<GatewayIntent> intents = EnumSet.noneOf(GatewayIntent.class);
        JDA jda = JDABuilder.createLight(token, intents).addEventListeners(messageListener).build();

        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(messageListener.allCommandData());
        commands.queue();
    }
}
