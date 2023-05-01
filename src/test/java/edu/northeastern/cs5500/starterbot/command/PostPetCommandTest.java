package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class PostPetCommandTest {
    @Test
    void testPostPetCommand() {
        PostPetCommand postPetCommand = new PostPetCommand();
        assertThat(postPetCommand.getName()).isEqualTo("postpetavailability");
        assertThat(postPetCommand.getCommandData().getName()).isEqualTo("postpetavailability");
    }
}
