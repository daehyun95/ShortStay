package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.listener.MessageListenerMethods;
import edu.northeastern.cs5500.starterbot.model.Property.HousingType;
import java.util.ArrayList;
import java.util.Date;
import net.dv8tion.jda.api.EmbedBuilder;
import org.junit.jupiter.api.Test;

class SubletListingTest {
    @Test
    void testSubletListing() {
        Address address = new Address();
        address.setZipCode("12345");
        Property property = new Property();
        property.setHousingType(HousingType.APARTMENT);
        property.setAddress(address);
        property.setPrice(1000);
        property.setBedrooms(1);
        property.setBathrooms(1);
        property.setHasParking(true);
        property.setPetsOK(true);
        property.setIsFurnished(false);
        property.setSquareFeet(500);

        Address address2 = new Address();
        address2.setZipCode("00000");
        Property property2 = new Property();
        property2.setHousingType(HousingType.CONDO);
        property2.setAddress(address2);
        property2.setPrice(700);
        property2.setBedrooms(2);
        property2.setBathrooms(1);
        property2.setHasParking(true);
        property2.setPetsOK(true);
        property2.setIsFurnished(true);
        property2.setSquareFeet(750);

        Address testAddress = new Address();
        testAddress.setZipCode("00000");
        Property testProperty = new Property();
        testProperty.setHousingType(HousingType.CONDO);
        testProperty.setAddress(testAddress);
        testProperty.setPrice(1050);
        testProperty.setBedrooms(2);
        testProperty.setBathrooms(1);
        testProperty.setHasParking(false);
        testProperty.setPetsOK(false);
        testProperty.setIsFurnished(false);
        testProperty.setSquareFeet(750);

        Users user = new Users();
        user.setStudentEmail("eddy@northeastern.edu");
        user.setPhoneNumber("2014524848");

        Users user2 = new Users();
        user.setStudentEmail("rei@northeastern.edu");
        user.setPhoneNumber("4525452012");

        Date startDate = new Date();
        Date endDate = new Date();
        Date testDate = new Date();

        SubletListing subletListing = new SubletListing();
        subletListing.setProperty(property);
        subletListing.setUser(user);
        subletListing.setStartDate(startDate);
        subletListing.setEndDate(endDate);

        SubletListing subletListing2 = new SubletListing();
        subletListing2.setProperty(property2);
        subletListing2.setUser(user2);
        subletListing2.setStartDate(startDate);
        subletListing2.setEndDate(endDate);

        SubletListing testSubletListing = new SubletListing();
        testSubletListing.setProperty(testProperty);
        testSubletListing.setUser(user2);
        testSubletListing.setStartDate(startDate);
        testSubletListing.setEndDate(endDate);

        Property property3 = new Property();
        property3.setHousingType(HousingType.CONDO);
        property3.setAddress(address);
        property3.setPrice(1050);
        property3.setBedrooms(1);
        property3.setBathrooms(1);
        property3.setHasParking(false);
        property3.setPetsOK(false);
        property3.setIsFurnished(false);
        property3.setSquareFeet(500);

        SubletListing subletListing3 = new SubletListing();
        subletListing3.setProperty(property3);
        subletListing3.setUser(user);
        subletListing3.setStartDate(startDate);
        subletListing3.setEndDate(endDate);

        Property property4 = new Property();
        property4.setHousingType(HousingType.CONDO);
        property4.setAddress(address);
        property4.setPrice(1050);
        property4.setBedrooms(1);
        property4.setBathrooms(1);
        property4.setHasParking(true);
        property4.setPetsOK(true);
        property4.setIsFurnished(true);
        property4.setSquareFeet(500);

        SubletListing subletListing4 = new SubletListing();
        subletListing4.setProperty(property4);
        subletListing4.setUser(user);
        subletListing4.setStartDate(startDate);
        subletListing4.setEndDate(endDate);

        Property property5 = new Property();
        property5.setHousingType(HousingType.HOUSE);
        property5.setAddress(address);
        property5.setPrice(500);
        property5.setBedrooms(1);
        property5.setBathrooms(1);
        property5.setHasParking(true);
        property5.setPetsOK(true);
        property5.setIsFurnished(true);
        property5.setSquareFeet(500);

        SubletListing subletListing5 = new SubletListing();
        subletListing5.setProperty(property5);
        subletListing5.setUser(user);
        subletListing5.setStartDate(startDate);
        subletListing5.setEndDate(endDate);

        EmbedBuilder embedBuilder = subletListing.returnSubletAsEmbedBuilder();
        assertThat(embedBuilder.getFields().get(0).getValue()).isEqualTo("APARTMENT");
        assertThat(embedBuilder.getFields().get(1).getValue()).isEqualTo("1000");
        assertThat(embedBuilder.getFields().get(2).getValue()).isEqualTo("1");
        assertThat(embedBuilder.getFields().get(3).getValue()).isEqualTo("1");
        assertThat(embedBuilder.getFields().get(4).getValue()).isEqualTo("Yes");
        assertThat(embedBuilder.getFields().get(5).getValue()).isEqualTo("Yes");
        assertThat(embedBuilder.getFields().get(6).getValue()).isEqualTo("No");
        assertThat(embedBuilder.getFields().get(7).getValue()).isEqualTo("500");
        MessageListenerMethods listenerMethods = new MessageListenerMethods();
        assertThat(embedBuilder.getFields().get(9).getValue())
                .isEqualTo(listenerMethods.convertToYYYYMMDD(testDate.toString()));
        assertThat(embedBuilder.getFields().get(10).getValue())
                .isEqualTo(listenerMethods.convertToYYYYMMDD(testDate.toString()));

        ArrayList<SubletListing> subletListings = new ArrayList<>();
        subletListings.add(subletListing);
        subletListings.add(subletListing2);

        ArrayList<SubletListing> filteredListings =
                subletListing.filterSubletListingsByZipDates(subletListings);
        assertThat(filteredListings.size()).isEqualTo(1);

        ArrayList<SubletListing> testFilteredListing =
                testSubletListing.filterSubletListingsByZipDates(subletListings);
        assertThat(testFilteredListing.size()).isEqualTo(1);

        ArrayList<SubletListing> filteredListings2 = subletListing.filterByPrice(subletListings);
        assertThat(filteredListings2.size()).isEqualTo(2);

        ArrayList<SubletListing> filteredListings3 = subletListing.filterByBools(subletListings);
        assertThat(filteredListings3.size()).isEqualTo(1);

        ArrayList<SubletListing> filteredListings4 =
                subletListing.filterByHousingType(subletListings);
        assertThat(filteredListings4.size()).isEqualTo(1);

        EmbedBuilder result1 = subletListing.returnClosestMatch(subletListings);
        assertThat(result1.getFields().get(13).getValue())
                .isEqualTo("Exact match found, please contact the student by phone or email.");

        ArrayList<SubletListing> subletListings2 = new ArrayList<>();

        EmbedBuilder result2 = subletListing.returnClosestMatch(subletListings2);
        assertThat(result2.getFields().get(13).getValue())
                .isEqualTo("No match found, start over with new details or try again later.");

        subletListings2.add(subletListing);
        subletListings2.add(subletListing2);

        EmbedBuilder result3 = subletListing3.returnClosestMatch(subletListings2);
        assertThat(result3.getFields().get(13).getValue())
                .isEqualTo(
                        "No exact match found, this was your closest match. Contact the student to secure housing or try again.");

        EmbedBuilder result4 = subletListing4.returnClosestMatch(subletListings2);
        assertThat(result4.getFields().get(13).getValue())
                .isEqualTo(
                        "No exact match found, this was your closest match. Contact the student to secure housing or try again.");

        EmbedBuilder result5 = subletListing5.returnClosestMatch(subletListings2);
        assertThat(result5.getFields().get(13).getValue())
                .isEqualTo(
                        "No exact match found, this was your closest match. Contact the student to secure housing or try again.");
    }
}
