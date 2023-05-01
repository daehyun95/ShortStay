package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class PostNumBedroomsTest {
    @Test
    void testPostNumBedrooms() {
        PostNumBedrooms postNumBedrooms = new PostNumBedrooms();
        assertThat(postNumBedrooms.getName()).isEqualTo("postnumbedrooms");
        assertThat(postNumBedrooms.getCommandData().getName()).isEqualTo("postnumbedrooms");
    }
}
