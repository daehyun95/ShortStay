package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class HelloCommandTest {
    @Test
    void testHelloResponse() {
        HelloCommand helloCommand = new HelloCommand();
        assertThat(helloCommand.getName()).isEqualTo("hello");
        assertThat(helloCommand.getCommandData().getName()).isEqualTo("hello");
    }
}
