package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class PostFinalDetailsCommandTest {
    @Test
    void testPostFinalDetailsCommand() {
        PostFinalDetailsCommand postFinalDetailsCommand = new PostFinalDetailsCommand();
        assertThat(postFinalDetailsCommand.getName()).isEqualTo("postfinaldetails");
        assertThat(postFinalDetailsCommand.getCommandData().getName())
                .isEqualTo("postfinaldetails");
    }
}
