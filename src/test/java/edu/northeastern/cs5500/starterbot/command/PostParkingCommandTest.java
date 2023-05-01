package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class PostParkingCommandTest {
    @Test
    void testPostParkingCommand() {
        PostParkingCommand postParkingCommand = new PostParkingCommand();
        assertThat(postParkingCommand.getName()).isEqualTo("postparkingspaceavailability");
        assertThat(postParkingCommand.getCommandData().getName())
                .isEqualTo("postparkingspaceavailability");
    }
}
