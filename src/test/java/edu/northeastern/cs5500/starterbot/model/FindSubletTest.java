package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import net.dv8tion.jda.api.EmbedBuilder;
import org.junit.jupiter.api.Test;

public class FindSubletTest {
    @Test
    void testFindSublet() {
        FindSublet findSublet1 = FindSublet.getInstance();

        findSublet1.addField("Name", "Eddy", true);
        EmbedBuilder embedBuilder = findSublet1.getEmbedBuilder();
        assertThat(embedBuilder.getFields().get(0).getName()).isEqualTo("Name");
        assertThat(embedBuilder.getFields().get(0).getValue()).isEqualTo("Eddy");
        assertThat(embedBuilder.getFields().get(0).isInline()).isTrue();

        assertThat(findSublet1.returnTempEmbed().getFields().get(0).getName()).isEqualTo("Name");
        assertThat(findSublet1.returnTempEmbed().getFields().get(0).getValue()).isEqualTo("Eddy");
        assertThat(findSublet1.returnTempEmbed().getFields().get(0).isInline()).isTrue();
        findSublet1.reset();
    }
}
