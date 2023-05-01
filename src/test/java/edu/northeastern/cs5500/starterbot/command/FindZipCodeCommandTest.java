package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class FindZipCodeCommandTest {
    @Test
    void testFindZipCodeCommand() {
        FindZipCodeCommand findZipCodeCommand = new FindZipCodeCommand();
        assertThat(findZipCodeCommand.getName()).isEqualTo("findzipcode");
        assertThat(findZipCodeCommand.getCommandData().getName()).isEqualTo("findzipcode");
    }
}
