package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class UsersTest {
    @Test
    void testUsers() {
        Users user1 = new Users();
        Users user2 = new Users();
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }
}
