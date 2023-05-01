package edu.northeastern.cs5500.starterbot.controller;

import com.mongodb.lang.Nullable;
import edu.northeastern.cs5500.starterbot.model.UserPreference;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.inject.Inject;

/*
 * The UserPreferenceController class is a public class that contains the information
 * necessary to get, add, update, delete, count, and retrieve a list of all
 * the userpreference objects stored in the userPreferenceRepository in MongoDB.
 */
public class UserPreferenceController {

    GenericRepository<UserPreference> userPreferenceRepository;

    /*
     * Controller constructor, requires the generic repository repository input.
     */
    @Inject
    UserPreferenceController(GenericRepository<UserPreference> userPreferenceRepository) {
        this.userPreferenceRepository = userPreferenceRepository;

        if (userPreferenceRepository.count() == 0) {
            UserPreference userPreference = new UserPreference();
            userPreference.setDiscordUserId("1234");
            userPreference.setPreferredName("Alex");
            userPreferenceRepository.add(userPreference);
        }
    }

    /*
     * Method to set a discord member's preferred name.
     *
     * Requires discordMemberId and preferredName, Strings.
     */
    public void setPreferredNameForUser(String discordMemberId, String preferredName) {
        UserPreference userPreference = getUserPreferenceForMemberId(discordMemberId);

        userPreference.setPreferredName(preferredName);
        userPreferenceRepository.update(userPreference);
    }

    /*
     * Getter for a discord member's preferred name, a String.
     */
    @Nullable
    public String getPreferredNameForUser(String discordMemberId) {
        return getUserPreferenceForMemberId(discordMemberId).getPreferredName();
    }

    /*
     * Getter for the user preferences based on their memberid.
     */
    @Nonnull
    public UserPreference getUserPreferenceForMemberId(String discordMemberId) {
        Collection<UserPreference> userPreferences = userPreferenceRepository.getAll();
        for (UserPreference currentUserPreference : userPreferences) {
            if (currentUserPreference.getDiscordUserId().equals(discordMemberId)) {
                return currentUserPreference;
            }
        }

        UserPreference userPreference = new UserPreference();
        userPreference.setDiscordUserId(discordMemberId);
        userPreferenceRepository.add(userPreference);
        return userPreference;
    }
}
