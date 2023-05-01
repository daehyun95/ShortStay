package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class PropertyEnumTest {
    private Property property = new Property();
    private static final String HOUSE = "HOUSE";
    private static final String APT = "APARTMENT";
    private static final String CONDO = "CONDO";

    @Test
    void testHouseType() {
        Property.HousingType house = Property.HousingType.valueOf(HOUSE);
        property.setHousingType(house);
        assertThat(property.getHousingType()).isEqualTo(Property.HousingType.HOUSE);
    }

    @Test
    void testApartmentType() {
        Property.HousingType apt = Property.HousingType.valueOf(APT);
        property.setHousingType(apt);
        assertThat(property.getHousingType()).isEqualTo(Property.HousingType.APARTMENT);
    }

    @Test
    void testCondoType() {
        Property.HousingType condo = Property.HousingType.valueOf(CONDO);
        property.setHousingType(condo);
        assertThat(property.getHousingType()).isEqualTo(Property.HousingType.CONDO);
    }
}
