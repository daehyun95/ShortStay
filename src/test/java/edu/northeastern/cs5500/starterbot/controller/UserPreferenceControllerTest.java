package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "MONGODB_URI", matches = ".+")
class UserPreferenceControllerTest {
    static final String USER_ID_1 = "discordUser235";
    static final String USER_ID_2 = "discordUser924";
    static final String PREFERRED_NAME_1 = "Josey";
    static final String PREFERRED_NAME_2 = "Josey2";

    private UserPreferenceController getUserPreferenceController() {
        return new UserPreferenceController(new InMemoryRepository<>());
    }

    @Test
    void testSetNameActuallySetsName() {
        // setup
        UserPreferenceController userPreferenceController = getUserPreferenceController();

        // precondition
        assertThat(userPreferenceController.getPreferredNameForUser(USER_ID_1))
                .isNotEqualTo(PREFERRED_NAME_1);

        // mutation
        userPreferenceController.setPreferredNameForUser(USER_ID_1, PREFERRED_NAME_1);

        // postcondition
        assertThat(userPreferenceController.getPreferredNameForUser(USER_ID_1))
                .isEqualTo(PREFERRED_NAME_1);
    }

    @Test
    void testSetNameOverwritesOldName() {
        UserPreferenceController userPreferenceController = getUserPreferenceController();
        userPreferenceController.setPreferredNameForUser(USER_ID_1, PREFERRED_NAME_1);
        assertThat(userPreferenceController.getPreferredNameForUser(USER_ID_1))
                .isEqualTo(PREFERRED_NAME_1);

        userPreferenceController.setPreferredNameForUser(USER_ID_1, PREFERRED_NAME_2);
        assertThat(userPreferenceController.getPreferredNameForUser(USER_ID_1))
                .isEqualTo(PREFERRED_NAME_2);
    }

    @Test
    void testSetNameOnlyOverwritesTargetUser() {
        UserPreferenceController userPreferenceController = getUserPreferenceController();

        userPreferenceController.setPreferredNameForUser(USER_ID_1, PREFERRED_NAME_1);
        userPreferenceController.setPreferredNameForUser(USER_ID_2, PREFERRED_NAME_2);

        assertThat(userPreferenceController.getPreferredNameForUser(USER_ID_1))
                .isEqualTo(PREFERRED_NAME_1);
        assertThat(userPreferenceController.getPreferredNameForUser(USER_ID_2))
                .isEqualTo(PREFERRED_NAME_2);
    }
}
