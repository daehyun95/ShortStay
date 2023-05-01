package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class PostSquareFeetCommandTest {
    @Test
    void testPostSquareFeetCommand() {
        PostSquareFeetCommand postSquareFeetCommand = new PostSquareFeetCommand();
        assertThat(postSquareFeetCommand.getName()).isEqualTo("postsquarefeet");
        assertThat(postSquareFeetCommand.getCommandData().getName()).isEqualTo("postsquarefeet");
    }
}
