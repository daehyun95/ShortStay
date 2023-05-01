package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.*;
import edu.northeastern.cs5500.starterbot.model.Property.HousingType;
import edu.northeastern.cs5500.starterbot.repository.MongoDBRepository;
import edu.northeastern.cs5500.starterbot.service.MongoDBService;
import java.util.Collection;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "MONGODB_URI", matches = ".+")
class SubletListingControllerTest {

    private SubletListingController getSubletListingController() {
        return new SubletListingController(
                new MongoDBRepository<SubletListing>(SubletListing.class, new MongoDBService()));
    }

    @Test
    void testSubletListingController() {
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

        Users user = new Users();
        user.setStudentEmail("eddy@northeastern.edu");
        user.setPhoneNumber("2014524848");

        Users user2 = new Users();
        user.setStudentEmail("rei@northeastern.edu");
        user.setPhoneNumber("4525452012");

        Date startDate = new Date();
        Date endDate = new Date();

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

        SubletListingController subletListingController = getSubletListingController();
        SubletListing addListing = subletListingController.addListing(subletListing);
        assertThat(addListing.getId()).isEqualTo(subletListing.getId());

        SubletListing getListing = subletListingController.getListing(addListing.getId());
        assertThat(getListing.getId()).isEqualTo(subletListing.getId());

        subletListingController.updateListing(addListing);
        assertThat(getListing.getId()).isEqualTo(subletListing.getId());

        subletListingController.deleteListing(addListing.getId());
        SubletListing deleteListing = subletListingController.getListing(addListing.getId());
        assertThat(deleteListing).isNull();

        subletListingController.addListing(subletListing);
        subletListingController.addListing(subletListing2);

        Collection<SubletListing> allListing = subletListingController.getAllListings();
        assertThat(allListing.size()).isNotNull();

        assertThat(subletListingController.countListings()).isNotNull();
    }
}
