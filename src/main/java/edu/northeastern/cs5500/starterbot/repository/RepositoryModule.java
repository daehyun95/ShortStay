package edu.northeastern.cs5500.starterbot.repository;

import dagger.Module;
import dagger.Provides;
import edu.northeastern.cs5500.starterbot.model.UserPreference;

/*
 * Repository Module class, defines the generic repository for user preference.
 */
@Module
public class RepositoryModule {

    /*
     * Generic repository for userpreference
     */
    @Provides
    public GenericRepository<UserPreference> provideUserPreferencesRepository(
            MongoDBRepository<UserPreference> repository) {
        return repository;
    }

    /*
     * Class of the user preference.
     */
    @Provides
    public Class<UserPreference> provideUserPreference() {
        return UserPreference.class;
    }
}
