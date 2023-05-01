package edu.northeastern.cs5500.starterbot.controller;

import static com.mongodb.client.model.Filters.*;

import edu.northeastern.cs5500.starterbot.model.*;
import edu.northeastern.cs5500.starterbot.repository.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import org.bson.types.ObjectId;

/*
 * The subletListingController class is a public class that contains the information
 * necessary to get, add, update, delete, count, and retrieve a list of all
 * the subletListing objects stored in the studensubletdatabase.
 *
 * Main function here is to add a SubletListing from the PostSublet instance or to
 * retrieve a list of valid SubletListings that match an input FindSublet SubletListing.
 */
public class SubletListingController {
    // Instance of the MongoDGRepository specific to the subletlisting objects.
    MongoDBRepository<SubletListing> subletListingRepository;

    /*
     * Controller constructor, requires the mongodb repository input.
     */
    @Inject
    public SubletListingController(MongoDBRepository<SubletListing> subletListingRepository) {
        this.subletListingRepository = subletListingRepository;
    }

    /*
     * Retrieves a subletlisting by its object id.
     */
    public SubletListing getListing(ObjectId id) {
        return subletListingRepository.get(id);
    }

    /*
     * Adds a subletlisting to the repository.
     */
    public SubletListing addListing(SubletListing listing) {
        subletListingRepository.add(listing);
        return listing;
    }

    /*
     * Updates a subletlisting already in the repository.
     */
    public void updateListing(SubletListing listing) {
        subletListingRepository.update(listing);
    }

    /*
     * Deletes a sublet listing by its object id.
     */
    public void deleteListing(ObjectId id) {
        subletListingRepository.delete(id);
    }

    /*
     * Retrieves all subletListings from the repository.
     */
    public Collection<SubletListing> getAllListings() {
        return new ArrayList<>(subletListingRepository.getAll());
    }

    /*
     * Counts all subletListings in the repository.
     */
    public long countListings() {
        return subletListingRepository.count();
    }
}
