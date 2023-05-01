package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

public class FindSquareFeetCommandTest {
    @Test
    void testFindSquareFeetCommand() {
        FindSquareFeetCommand findSquareFeetCommand = new FindSquareFeetCommand();
        assertThat(findSquareFeetCommand.getName()).isEqualTo("findsquarefeet");
        assertThat(findSquareFeetCommand.getCommandData().getName()).isEqualTo("findsquarefeet");
    }
}
