package edu.northeastern.cs5500.starterbot.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/*
 * Class representing a student with their student email and phone number, Strings.
 */
@Data
@RequiredArgsConstructor
public class Students {
    private String studentEmail;
    private String phoneNumber;
}
