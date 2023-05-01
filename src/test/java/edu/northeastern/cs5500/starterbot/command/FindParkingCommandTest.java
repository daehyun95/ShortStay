package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class FindParkingCommandTest {
    @Test
    void testFindParkingCommand() {
        FindParkingCommand findParkingCommand = new FindParkingCommand();
        assertThat(findParkingCommand.getName()).isEqualTo("findparkingspaceavailability");
        assertThat(findParkingCommand.getCommandData().getName())
                .isEqualTo("findparkingspaceavailability");
    }
}
