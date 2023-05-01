package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class FindPetCommandTest {
    @Test
    void testFindPetCommand() {
        FindPetCommand findPetCommand = new FindPetCommand();
        assertThat(findPetCommand.getName()).isEqualTo("findpetavailability");
        assertThat(findPetCommand.getCommandData().getName()).isEqualTo("findpetavailability");
    }
}
