package org.cdl.service;

import org.cdl.object.PriceScheme;
import org.cdl.object.Product;
import org.cdl.util.Codes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SetupServiceImplTest {

    private SetupService setupService;
    private Map<String, List<PriceScheme>> schemeMap;

    @BeforeEach
    void setUp() {
        schemeMap = new HashMap<>();
        setupService = Mockito.spy(new SetupServiceImpl(schemeMap));
    }


    @ParameterizedTest
    @CsvSource(value = {"1:50", "3:130"}, delimiter = ':')
    void addSchemeTest_single(int quantity, int price) {
        // method invocation
        Product product = new Product(Codes.A.getCode());
        product.setUnitPrice(50);
        PriceScheme priceScheme = new PriceScheme(product);
        priceScheme.setQuantity(quantity);
        priceScheme.setPrice(price);
        setupService.addScheme(priceScheme);

        // assertions
        List<PriceScheme> priceSchemes = schemeMap.get(Codes.A.getCode());
        assertThat(priceSchemes).hasSize(1);
        assertThat(priceSchemes.get(0).getQuantity()).isEqualTo(quantity);
        assertThat(priceSchemes.get(0).getPrice()).isEqualTo(price);
        assertThat(priceSchemes.get(0).getProduct().getUnitPrice()).isEqualTo(50);
        assertThat(priceSchemes.get(0).getProduct().getCode()).isEqualTo(Codes.A.getCode());
    }

    @Test
    void addSchemeTest_multiple_sort() {
        // method invocation
        Product product = new Product(Codes.A.getCode());
        product.setUnitPrice(50);
        PriceScheme priceScheme1 = new PriceScheme(product);
        priceScheme1.setQuantity(1);
        priceScheme1.setPrice(50);
        PriceScheme priceScheme2 = new PriceScheme(product);
        priceScheme2.setQuantity(5);
        priceScheme2.setPrice(200);
        PriceScheme priceScheme3 = new PriceScheme(product);
        priceScheme3.setQuantity(3);
        priceScheme3.setPrice(130);
        setupService.addScheme(priceScheme1);
        setupService.addScheme(priceScheme2);
        setupService.addScheme(priceScheme3);

        // assertions : schemes should be sorted in descending  order based on quantity
        List<PriceScheme> priceSchemes = schemeMap.get(Codes.A.getCode());
        assertThat(priceSchemes).hasSize(3);
        assertThat(priceSchemes.get(0).getQuantity()).isEqualTo(5);
        assertThat(priceSchemes.get(0).getPrice()).isEqualTo(200);
        assertThat(priceSchemes.get(1).getQuantity()).isEqualTo(3);
        assertThat(priceSchemes.get(1).getPrice()).isEqualTo(130);
        assertThat(priceSchemes.get(2).getQuantity()).isEqualTo(1);
        assertThat(priceSchemes.get(2).getPrice()).isEqualTo(50);

    }

    @Test
    void readSchemesTest() {
        // method invocation
        Product productA = new Product(Codes.A.getCode());
        productA.setUnitPrice(50);
        Product productB = new Product(Codes.B.getCode());
        productA.setUnitPrice(30);
        PriceScheme priceScheme1 = new PriceScheme(productA);
        priceScheme1.setQuantity(1);
        priceScheme1.setPrice(50);
        PriceScheme priceScheme2 = new PriceScheme(productA);
        priceScheme2.setQuantity(3);
        priceScheme2.setPrice(130);
        PriceScheme priceScheme3 = new PriceScheme(productB);
        priceScheme3.setQuantity(2);
        priceScheme3.setPrice(45);
        setupService.addScheme(priceScheme1);
        setupService.addScheme(priceScheme2);
        setupService.addScheme(priceScheme3);
        List<PriceScheme> priceSchemesA = setupService.readSchemes(Codes.A.getCode());
        List<PriceScheme> priceSchemesB = setupService.readSchemes(Codes.B.getCode());

        // assertions : product A
        assertThat(priceSchemesA).hasSize(2);
        assertThat(priceSchemesA.get(0).getQuantity()).isEqualTo(3);
        assertThat(priceSchemesA.get(0).getPrice()).isEqualTo(130);
        assertThat(priceSchemesA.get(1).getQuantity()).isEqualTo(1);
        assertThat(priceSchemesA.get(1).getPrice()).isEqualTo(50);

        // assertions : product B
        assertThat(priceSchemesB).hasSize(1);
        assertThat(priceSchemesB.get(0).getQuantity()).isEqualTo(2);
        assertThat(priceSchemesB.get(0).getPrice()).isEqualTo(45);
    }


}
