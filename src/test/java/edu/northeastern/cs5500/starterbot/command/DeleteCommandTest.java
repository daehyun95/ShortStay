package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.junit.jupiter.api.Test;

public class DeleteCommandTest {
    @Test
    void testNameMatchesDat() {
        DeleteCommand deleteCommand = new DeleteCommand();
        String name = deleteCommand.getName();
        CommandData commandData = deleteCommand.getCommandData();

        assertThat(name).isEqualTo(commandData.getName());
    }
}
