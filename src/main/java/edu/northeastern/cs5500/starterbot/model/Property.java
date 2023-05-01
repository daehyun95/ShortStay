package edu.northeastern.cs5500.starterbot.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/*
 * Class representing a property includes its type(apartment, condo, house) address, price
 * number of bedrooms and bathrooms, parking availability, pet-friendliness,
 * whether it is furnished and the square footage of the property
 */
@Data
@RequiredArgsConstructor
public class Property {
    private HousingType housingType;
    private Address address;
    private Integer price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Boolean hasParking;
    private Boolean petsOK;
    private Boolean isFurnished;
    private Integer squareFeet;

    /*
     * An enumeration of the different types of housing
     */
    public enum HousingType {
        APARTMENT,
        CONDO,
        HOUSE;
    }
}
