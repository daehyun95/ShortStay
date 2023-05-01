package edu.northeastern.cs5500.starterbot.listener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/*
 * MessageListernerMethods class is a public java class that contains public methods required
 * in the MessageListener class.
 *
 * The purpose of these functions is to validate user inputs using regex, comparisons,
 * and to convert dates from/to strings, from/to Date()s, and from/to MongoDB Dates()s
 */
public class MessageListenerMethods {
    private static final String ZIPCODEREGEX = "^[0-9]{5}$";
    private static final String EMAILREGEX = "[a-zA-Z0-9_%+-]+@northeastern.edu$";
    private static final String DATEREGEX =
            "[1-9][0-9][0-9]{2}-([0][1-9]|[1][0-2])-([1-2][0-9]|[0][1-9]|[3][0-1])$";
    private static final String PHONEREGEX = "^[0-9]{10}$";
    private static final LocalDate CURRENTDATE = LocalDate.now();

    /*
     * Method that receives string email, phone, and LocalDate and startDate for a /post command
     *
     * Requires: email (String), phone (String), startDate (String), endDate (String).
     * Returns: Error message if a string is invalid or null upon success.
     */
    public String validatePostInput(
            String zipcode, String email, String phone, String startDate, String endDate) {
        if (!zipcode.matches(ZIPCODEREGEX)) return "Only put numeric value in zipcode\n Try Again!";
        if (!email.matches(EMAILREGEX)) return "Email must end in @northeastern.edu\n Try Again!";
        if (!phone.matches(PHONEREGEX)) return "Only put numeric value in phone\n Try Again!";

        // String date will be in ISO format
        if (!startDate.matches(DATEREGEX)
                || convertDateStringToLocalDate(startDate).isBefore(CURRENTDATE))
            return "Start date must be in YYYY-MM-DD format and cannot be before than the current date\n Try Again!";
        if (!endDate.matches(DATEREGEX)
                || convertDateStringToLocalDate(endDate)
                        .isBefore(convertDateStringToLocalDate(startDate)))
            return "End date must be in YYYY-MM-DD format and cannot be before than the start date\n Try Again!";
        return null;
    }

    /*
     * Method that receives inputs (zipcode, startdate, enddate) (String, String, String) for a /find command
     *
     * Returns: Error message if a string is invalid or null upon success.
     */
    public String validateFindInput(String zipcode, String startDate, String endDate) {
        if (!zipcode.matches(ZIPCODEREGEX)) return "Only put numeric value in zipcode\n Try Again!";
        if (!startDate.matches(DATEREGEX)
                || convertDateStringToLocalDate(startDate).isBefore(CURRENTDATE))
            return "Start date must be in YYYY-MM-DD format and cannot be before than the current date\n Try Again!";
        if (!endDate.matches(DATEREGEX)
                || convertDateStringToLocalDate(endDate)
                        .isBefore(convertDateStringToLocalDate(startDate)))
            return "End date must be in YYYY-MM-DD format and cannot be before than the start date\n Try Again!";
        return null;
    }

    /*
     * Method that will convert a YYYY-MM-DD string date to a local date for easy comparisons.
     */
    public LocalDate convertDateStringToLocalDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate returnDate = LocalDate.parse(dateString, formatter);
            return returnDate;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * Method that will convert a date string to a Date() that matches the dates stored in
     * MongoDB.
     */
    public Date convertDateStringToMongoDate(String dateString) throws Exception {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date returnDate = inputFormat.parse(dateString);
            return returnDate;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * Function that takes in a string date in the format of YYYY-MM-DD and will
     * return a Date
     */
    public Date convertISOStringDateToDate(String inputDate) {
        try {
            LocalDate localInputDate = convertISO8601ToLocalDate(inputDate);
            return Date.from(localInputDate.atStartOfDay(ZoneOffset.UTC).toInstant());
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * Function that will take in a String iso8601Date and return a LocalDate
     */
    public LocalDate convertISO8601ToLocalDate(String iso8601DateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime localDateTime = LocalDateTime.parse(iso8601DateString, formatter);
            LocalDate returnDate = localDateTime.toLocalDate();
            return returnDate;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * Method that takes in a Date() from MongoDB, and returns a YYYYY-MM-DD
     * String date for embed building.
     */
    public String convertToYYYYMMDD(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
