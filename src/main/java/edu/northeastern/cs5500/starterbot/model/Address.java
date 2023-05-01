package edu.northeastern.cs5500.starterbot.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/*
 * Class representing an Address
 * It has single instance variable: zipcode
 */
@Data
@RequiredArgsConstructor
public class Address {
    // Zipcode, a the property zipcode for a sublet listing, a String
    private String zipCode;
}
