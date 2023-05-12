package org.cdl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    @ParameterizedTest
    @CsvSource(value = {"50P:0.50",
            "250P:2.50",
            "£50:50",
            "70:70"}, delimiter = ':')
    void preprocessPriceTest(String priceStr, double price) {
        assertThat(Main.preprocessPrice(priceStr)).isEqualTo(price);
    }
}