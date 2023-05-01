package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

class FindNumBathroomsTest {
    @Test
    void testFindNumBathrooms() {
        FindNumBathrooms findNumBathrooms = new FindNumBathrooms();
        assertThat(findNumBathrooms.getName()).isEqualTo("findnumbathrooms");
        assertThat(findNumBathrooms.getCommandData().getName()).isEqualTo("findnumbathrooms");
    }
}
