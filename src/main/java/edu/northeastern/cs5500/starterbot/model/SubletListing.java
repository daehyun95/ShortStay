package edu.northeastern.cs5500.starterbot.model;

// Need to utiliza a public method in the import below to convert for embedbuilder.
import edu.northeastern.cs5500.starterbot.listener.MessageListenerMethods;
import edu.northeastern.cs5500.starterbot.repository.*;
import java.awt.Color;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bson.types.ObjectId;

/*
 * Class containintg the information for a simple java object: SubletListing
 * Requires an ID, property, user listing, start and end dates.
 *
 * Class methods:
 * returnSubletAsEmbedBuilder() will return all details as an embed builder
 * to perform event.reply() with the embed for user view
 *
 * Filtering methods: Will filter the MongoDB collection of subletListings based on field priority
 * will return an embed with an exact match, the closest match, or a no match description.
 */
@Data
@RequiredArgsConstructor
public class SubletListing implements Model {
    private static final MessageListenerMethods listenerMethods = new MessageListenerMethods();
    /* Constants related to the embed builders */
    private static final String TITLE = "Sublet Listing Details";
    private static final String RESULT = "Result: ";

    private static final String CLOSEST_DX =
            "No exact match found, this was your closest match. Contact the student to secure housing or try again.";
    private static final String NO_MATCH_DX =
            "No match found, start over with new details or try again later.";
    private static final String MATCH_DX =
            "Exact match found, please contact the student by phone or email.";

    /* Basic constants */
    private static final Integer DAY_MARGIN = 14;
    private static final long MSEC_IN_DAY = 86400000L;
    private static final Integer PRICE_MARGIN = 100;
    private static final Boolean F = false;

    /* Fields for the SubletListing class */
    private ObjectId Id;
    private Property property;
    private Users user;
    private Date startDate;
    private Date endDate;

    /*
     * Public function that will return an embed version of the subletListing.
     */
    public EmbedBuilder returnSubletAsEmbedBuilder() {
        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.ORANGE).setTitle(TITLE);
        embedBuilder.addField("Housing Type: ", this.property.getHousingType().toString(), F);
        embedBuilder.addField("Price: ", this.property.getPrice().toString(), F);
        embedBuilder.addField("Bedrooms: ", this.property.getBedrooms().toString(), F);
        embedBuilder.addField("Bathrooms: ", this.property.getBathrooms().toString(), F);
        embedBuilder.addField(
                "Parking available: ", this.interpretBoolean(this.property.getHasParking()), F);
        embedBuilder.addField(
                "Pets are allowed: ", this.interpretBoolean(this.property.getPetsOK()), F);
        embedBuilder.addField(
                "The room is furnished: ",
                this.interpretBoolean(this.property.getIsFurnished()),
                F);
        embedBuilder.addField(
                "Estimated Size (sq ft): ", this.property.getSquareFeet().toString(), F);

        embedBuilder.addField("Zipcode: ", this.property.getAddress().getZipCode(), F);

        // For the user, will convert ISO08601 String to LocalDate for user to view easily.
        embedBuilder.addField(
                "Available starting: ",
                listenerMethods.convertToYYYYMMDD(this.startDate.toString()),
                F);
        embedBuilder.addField(
                "Available until: ", listenerMethods.convertToYYYYMMDD(this.endDate.toString()), F);

        // Only includes information if the student exists.
        if ((this.getUser()) != null) {
            embedBuilder.addField("Contact email: ", this.user.getStudentEmail(), F);
            embedBuilder.addField("Contact phone: ", this.user.getPhoneNumber(), F);
        }

        return embedBuilder;
    }

    /*
     * Function that will interpret an input boolean and return a string
     * yes for true and no for false.
     */
    private String interpretBoolean(boolean inputBoolean) {
        if (inputBoolean) {
            return "Yes";
        }
        return "No";
    }

    /*
     * Method that requires the MongoDB collection in ArrayList format.
     * Will call manual filtering functions based on importance and will return the embed
     * of the closest match.
     */
    public EmbedBuilder returnClosestMatch(ArrayList<SubletListing> subletListings) {

        // If the collection is empty, no match can be found
        if (subletListings.isEmpty()) {
            EmbedBuilder result = this.returnSubletAsEmbedBuilder();
            return result.addField(RESULT, NO_MATCH_DX, F);
        }

        // If collection is not empty, start by filtering by zipcode and dates
        ArrayList<SubletListing> previousFilter =
                this.filterSubletListingsByZipDates(subletListings);

        // Check if the first filter is empty
        if (previousFilter.isEmpty()) {
            EmbedBuilder result = this.returnSubletAsEmbedBuilder();
            return result.addField(RESULT, NO_MATCH_DX, F);
        }

        // Apply price filter
        ArrayList<SubletListing> nextFilter = this.filterByPrice(previousFilter);
        if (nextFilter.isEmpty()) {
            return this.returnClosestMatchHelper(previousFilter);
        }

        // Apply boolean filter
        previousFilter = nextFilter;
        nextFilter = this.filterByBools(nextFilter);
        if (nextFilter.isEmpty()) {
            return this.returnClosestMatchHelper(previousFilter);
        }

        // Apply housing type
        previousFilter = nextFilter;
        nextFilter = this.filterByHousingType(nextFilter);
        if (nextFilter.isEmpty()) {
            return this.returnClosestMatchHelper(previousFilter);
        }

        // Return result
        SubletListing resultSublet = nextFilter.get(0);
        return resultSublet.returnSubletAsEmbedBuilder().addField(RESULT, MATCH_DX, F);
    }

    private EmbedBuilder returnClosestMatchHelper(ArrayList<SubletListing> subletListings) {
        SubletListing resultSublet = subletListings.get(0);
        return resultSublet.returnSubletAsEmbedBuilder().addField(RESULT, CLOSEST_DX, F);
    }

    /* Method that performs the first round of filtering. Will filter the collection based on zipcode
     * and within 14 days of the start and end dates.
     */
    public ArrayList<SubletListing> filterSubletListingsByZipDates(
            ArrayList<SubletListing> subletListings) {
        ArrayList<SubletListing> filteredListings = new ArrayList<>();
        for (SubletListing subletListing : subletListings) {
            if (subletListing
                            .getProperty()
                            .getAddress()
                            .getZipCode()
                            .equals(this.getProperty().getAddress().getZipCode())
                    && subletListing.getStartDate().getTime()
                            >= this.startDate.getTime()
                                    - DAY_MARGIN
                                            * MSEC_IN_DAY // checking for sublets w/start date 14
                    // days before
                    && subletListing.getEndDate().getTime()
                            <= this.endDate.getTime()
                                    + DAY_MARGIN
                                            * MSEC_IN_DAY) // checking for sublets w/end date 14
            // days after
            {
                filteredListings.add(subletListing);
            }
        }
        return filteredListings;
    }

    /*
     * Second round of filtering for price, will only include properties within 100 dollars of
     * the FindSublet subletlisting price.
     */
    public ArrayList<SubletListing> filterByPrice(ArrayList<SubletListing> subletListings) {
        ArrayList<SubletListing> filteredListings = new ArrayList<>();
        for (SubletListing subletListing : subletListings) {
            if (subletListing.getProperty().getPrice()
                    <= this.getProperty().getPrice() + PRICE_MARGIN) {
                filteredListings.add(subletListing);
            }
        }
        return filteredListings;
    }

    /*
     * Third round of filtering for booleans.
     */
    public ArrayList<SubletListing> filterByBools(ArrayList<SubletListing> subletListings) {
        ArrayList<SubletListing> filteredListings = new ArrayList<>();
        for (SubletListing subletListing : subletListings) {
            if ((subletListing.getProperty().getHasParking() == this.getProperty().getHasParking())
                    && (subletListing.getProperty().getPetsOK() == this.getProperty().getPetsOK())
                    && (subletListing.getProperty().getIsFurnished()
                            == this.getProperty().getIsFurnished())) {
                filteredListings.add(subletListing);
            }
        }
        return filteredListings;
    }

    /*
     * Final round of filtering, housing type.
     */
    public ArrayList<SubletListing> filterByHousingType(ArrayList<SubletListing> subletListings) {
        ArrayList<SubletListing> filteredListings = new ArrayList<>();
        for (SubletListing subletListing : subletListings) {
            if (subletListing.getProperty().getHousingType()
                    == this.getProperty().getHousingType()) {
                filteredListings.add(subletListing);
            }
        }
        return filteredListings;
    }
}
