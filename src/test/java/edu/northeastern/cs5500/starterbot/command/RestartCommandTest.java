package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class RestartCommandTest {
    @Test
    void testRestartCommand() {
        RestartCommand restartCommand = new RestartCommand();
        assertThat(restartCommand.getName()).isEqualTo("restart");
        assertThat(restartCommand.getCommandData().getName()).isEqualTo("restart");
    }
}
