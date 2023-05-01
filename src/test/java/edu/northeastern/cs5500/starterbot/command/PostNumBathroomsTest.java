package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

class PostNumBathroomsTest {
    @Test
    void testPostNumBathrooms() {
        PostNumBathrooms postNumBathrooms = new PostNumBathrooms();
        assertThat(postNumBathrooms.getName()).isEqualTo("postnumbathrooms");
        assertThat(postNumBathrooms.getCommandData().getName()).isEqualTo("postnumbathrooms");
    }
}
