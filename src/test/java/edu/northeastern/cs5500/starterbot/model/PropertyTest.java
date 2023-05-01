package edu.northeastern.cs5500.starterbot.model;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Property.HousingType;
import org.junit.jupiter.api.Test;

class PropertyTest {
    @Test
    void testProperty() {
        HousingType type1 = HousingType.APARTMENT;
        assertThat(type1.toString()).isEqualTo("APARTMENT");
        HousingType type2 = HousingType.CONDO;
        assertThat(type2.toString()).isEqualTo("CONDO");
        HousingType type3 = HousingType.HOUSE;
        assertThat(type3.toString()).isEqualTo("HOUSE");
    }
}
