package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import org.junit.jupiter.api.Test;

/*
 * NOTE: All find and post commands will only be tested at this basic level, because
 * setting up testing for the commands requires Nonnul events.
 *
 * We were instructed to not use Mockito, thus the the bulk of these commands will not be
 * as thoroughly tested as we would prefer.
 */
class PostCommandTest {
    @Test
    void testPostCommand() {
        PostCommand postCommand = new PostCommand();
        assertThat(postCommand.getName()).isEqualTo("post");
        assertThat(postCommand.getCommandData().getName()).isEqualTo("post");

        PostSublet sublet = PostSublet.getInstance();
        sublet.reset();
        postCommand.newPostSublet();
        assertThat(sublet).isEqualTo(PostSublet.getInstance());
    }
}
