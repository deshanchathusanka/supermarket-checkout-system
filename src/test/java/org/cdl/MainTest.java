package org.cdl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MainTest {

    @ParameterizedTest
    @CsvSource(value = {"50P:0.50",
            "250P:2.50",
            "£50:50",
            "70:70"}, delimiter = ':')
    void preprocessPriceTest(String priceStr, double price) {
        assertThat(Main.preprocessPrice(priceStr)).isEqualTo(price);
    }

    @ParameterizedTest
    @CsvSource(value = {"50K:0.50",
            "250HP:2.50",
            "$50:50",
            "Y70:70"}, delimiter = ':')
    void preprocessPriceTest_exception(String priceStr, double price) {
        assertThatThrownBy(() -> Main.preprocessPrice(priceStr)).isInstanceOf(NumberFormatException.class);
    }
}