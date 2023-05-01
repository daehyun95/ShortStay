package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class FindNumBedroomsTest {
    @Test
    void testFindNumBedrooms() {
        FindNumBedrooms findNumBedrooms = new FindNumBedrooms();
        assertThat(findNumBedrooms.getName()).isEqualTo("findnumbedrooms");
        assertThat(findNumBedrooms.getCommandData().getName()).isEqualTo("findnumbedrooms");
    }
}
