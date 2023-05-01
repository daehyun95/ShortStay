package edu.northeastern.cs5500.starterbot.listener;

import static com.google.common.truth.Truth.assertThat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;

class MessageListernerMethodTest {
    @Test
    void testValidatePostInput() {
        MessageListenerMethods messageListenerMethods = new MessageListenerMethods();
        String zipCode = "sdfef";
        String email = "test@els.edu";
        String phone = "122123";
        String startDate = "222";
        String endDate = "23r4";

        String result =
                messageListenerMethods.validatePostInput(zipCode, email, phone, startDate, endDate);
        assertThat(result).isEqualTo("Only put numeric value in zipcode\n Try Again!");

        zipCode = "05125";
        result =
                messageListenerMethods.validatePostInput(zipCode, email, phone, startDate, endDate);
        assertThat(result).isEqualTo("Email must end in @northeastern.edu\n Try Again!");

        email = "df@northeastern.edu";
        result =
                messageListenerMethods.validatePostInput(zipCode, email, phone, startDate, endDate);
        assertThat(result).isEqualTo("Only put numeric value in phone\n Try Again!");

        phone = "2014214797";
        result =
                messageListenerMethods.validatePostInput(zipCode, email, phone, startDate, endDate);
        assertThat(result)
                .isEqualTo(
                        "Start date must be in YYYY-MM-DD format and cannot be before than the current date\n Try Again!");

        startDate = "2020-05-01";
        result =
                messageListenerMethods.validatePostInput(zipCode, email, phone, startDate, endDate);
        assertThat(result)
                .isEqualTo(
                        "Start date must be in YYYY-MM-DD format and cannot be before than the current date\n Try Again!");

        startDate = "2025-05-01";
        result =
                messageListenerMethods.validatePostInput(zipCode, email, phone, startDate, endDate);
        assertThat(result)
                .isEqualTo(
                        "End date must be in YYYY-MM-DD format and cannot be before than the start date\n Try Again!");

        endDate = "2020-05-15";
        result =
                messageListenerMethods.validatePostInput(zipCode, email, phone, startDate, endDate);
        assertThat(result)
                .isEqualTo(
                        "End date must be in YYYY-MM-DD format and cannot be before than the start date\n Try Again!");

        endDate = "2025-05-15";
        result =
                messageListenerMethods.validatePostInput(zipCode, email, phone, startDate, endDate);
        assertThat(result).isEqualTo(null);
    }

    @Test
    void testValidateFindInput() {
        MessageListenerMethods messageListenerMethods = new MessageListenerMethods();
        String zipCode = "sdfef";
        String startDate = "222";
        String endDate = "23r4";

        String result = messageListenerMethods.validateFindInput(zipCode, startDate, endDate);
        assertThat(result).isEqualTo("Only put numeric value in zipcode\n Try Again!");

        zipCode = "08152";
        result = messageListenerMethods.validateFindInput(zipCode, startDate, endDate);
        assertThat(result)
                .isEqualTo(
                        "Start date must be in YYYY-MM-DD format and cannot be before than the current date\n Try Again!");

        startDate = "2020-05-01";
        result = messageListenerMethods.validateFindInput(zipCode, startDate, endDate);
        assertThat(result)
                .isEqualTo(
                        "Start date must be in YYYY-MM-DD format and cannot be before than the current date\n Try Again!");

        startDate = "2025-05-01";
        result = messageListenerMethods.validateFindInput(zipCode, startDate, endDate);
        assertThat(result)
                .isEqualTo(
                        "End date must be in YYYY-MM-DD format and cannot be before than the start date\n Try Again!");

        endDate = "2020-05-15";
        result = messageListenerMethods.validateFindInput(zipCode, startDate, endDate);
        assertThat(result)
                .isEqualTo(
                        "End date must be in YYYY-MM-DD format and cannot be before than the start date\n Try Again!");

        endDate = "2025-05-15";
        result = messageListenerMethods.validateFindInput(zipCode, startDate, endDate);
        assertThat(result).isEqualTo(null);
    }

    @Test
    void testconvertDateStringToLocalDate() {
        MessageListenerMethods messageListenerMethods = new MessageListenerMethods();
        String date = "2023-05-01";
        LocalDate expectDate = LocalDate.of(2023, 05, 01);
        LocalDate actualDate = messageListenerMethods.convertDateStringToLocalDate(date);
        assertThat(actualDate).isEqualTo(expectDate);

        date = "02";
        actualDate = messageListenerMethods.convertDateStringToLocalDate(date);
        assertThat(actualDate).isEqualTo(null);
    }

    @Test
    void convertDateStringToMongoDate() throws Exception {
        MessageListenerMethods messageListenerMethods = new MessageListenerMethods();
        String date = "2023-05-01";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectDate = inputFormat.parse(date);
        Date actualDate = messageListenerMethods.convertDateStringToMongoDate(date);
        assertThat(actualDate).isEqualTo(expectDate);

        date = "02";
        actualDate = messageListenerMethods.convertDateStringToMongoDate(date);
        assertThat(actualDate).isEqualTo(null);
    }

    @Test
    void convertISOStringDateToDate() {
        MessageListenerMethods messageListenerMethods = new MessageListenerMethods();
        String date = "2023-05-01T00:00:00.000Z";
        Date expectDate =
                Date.from(LocalDate.of(2023, 05, 01).atStartOfDay(ZoneOffset.UTC).toInstant());
        Date actualDate = messageListenerMethods.convertISOStringDateToDate(date);
        assertThat(actualDate).isEqualTo(expectDate);

        date = "2023-05-01";
        actualDate = messageListenerMethods.convertISOStringDateToDate(date);
        assertThat(actualDate).isEqualTo(null);
    }

    @Test
    void convertISO8601ToLocalDate() {
        MessageListenerMethods messageListenerMethods = new MessageListenerMethods();
        String date = "2023-05-01T00:00:00.000Z";
        LocalDate expectDate = LocalDate.of(2023, 05, 01);
        LocalDate actualDate = messageListenerMethods.convertISO8601ToLocalDate(date);
        assertThat(actualDate).isEqualTo(expectDate);

        date = "2023-05-01";
        actualDate = messageListenerMethods.convertISO8601ToLocalDate(date);
        assertThat(actualDate).isEqualTo(null);
    }

    @Test
    void convertToYYYYMMDD() {
        MessageListenerMethods messageListenerMethods = new MessageListenerMethods();
        String date = "Mon May 01 00:00:00 UTC 2023";
        String expectDate = "2023-05-01";
        String actualDate = messageListenerMethods.convertToYYYYMMDD(date);
        assertThat(actualDate).isEqualTo(expectDate);

        date = "2023-05-01";
        actualDate = messageListenerMethods.convertToYYYYMMDD(date);
        assertThat(actualDate).isEqualTo(null);
    }
}
