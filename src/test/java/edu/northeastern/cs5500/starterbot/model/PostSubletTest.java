package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class PostSubletTest {
    @Test
    void testPostSublet() {
        PostSublet postSublet1 = PostSublet.getInstance();
        PostSublet postSublet2 = PostSublet.getInstance();
        assertThat(postSublet1).isEqualTo(postSublet2);

        postSublet1.addField("Name", "Eddy", true);
        assertThat(postSublet1.getEmbedBuilder().getFields().get(0).getName()).isEqualTo("Name");
        assertThat(postSublet1.getEmbedBuilder().getFields().get(0).getValue()).isEqualTo("Eddy");
        assertThat(postSublet1.getEmbedBuilder().getFields().get(0).isInline()).isTrue();

        assertThat(postSublet1.returnTempEmbed().getFields().get(0).getName()).isEqualTo("Name");
        assertThat(postSublet1.returnTempEmbed().getFields().get(0).getValue()).isEqualTo("Eddy");
        assertThat(postSublet1.returnTempEmbed().getFields().get(0).isInline()).isTrue();

        postSublet1.reset();
        assertThat(postSublet1).isEqualTo(postSublet2);
    }
}
