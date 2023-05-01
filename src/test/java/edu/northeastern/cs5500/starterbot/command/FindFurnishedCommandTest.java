package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

class FindFurnishedCommandTest {
    @Test
    void testFindFurnished() {
        FindFurnishedCommand findFurnishedCommand = new FindFurnishedCommand();
        assertThat(findFurnishedCommand.getName()).isEqualTo("findisfurnished");
        assertThat(findFurnishedCommand.getCommandData().getName()).isEqualTo("findisfurnished");
    }
}
