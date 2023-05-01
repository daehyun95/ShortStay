package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

class PostFurnishedCommandTest {
    @Test
    void testPostFurnished() {
        PostFurnishedCommand postFurnishedCommand = new PostFurnishedCommand();
        assertThat(postFurnishedCommand.getName()).isEqualTo("postisfurnished");
        assertThat(postFurnishedCommand.getCommandData().getName()).isEqualTo("postisfurnished");
    }
}
