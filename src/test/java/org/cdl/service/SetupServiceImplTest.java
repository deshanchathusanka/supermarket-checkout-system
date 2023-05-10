package org.cdl.service;

import org.cdl.object.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SetupServiceImplTest {

    private SetupService setupService;
    private Map<String, List<PriceScheme>> schemeMap;

    @BeforeEach
    void setUp() {
        schemeMap = new HashMap<>();
        setupService = Mockito.spy(new SetupServiceImpl(schemeMap));
    }


    @Test
    void addSchemeTest_single_item() {
        // method invocation
        PriceScheme priceScheme = new PriceScheme();
        Product product = new Product(Product.Codes.A.getCode());
        product.setUnitPrice(50);
        priceScheme.setProduct(product);
        priceScheme.setQuantity(1);
        priceScheme.setPrice(50);
        setupService.addScheme(priceScheme);

        // assertions
        List<PriceScheme> priceSchemes = schemeMap.get(Product.Codes.A.getCode());
        assertThat(priceSchemes).hasSize(1);
        assertThat(priceSchemes.get(0).getQuantity()).isEqualTo(1);
        assertThat(priceSchemes.get(0).getPrice()).isEqualTo(50);
        assertThat(priceSchemes.get(0).getProduct().getUnitPrice()).isEqualTo(50);
        assertThat(priceSchemes.get(0).getProduct().getCode()).isEqualTo(Product.Codes.A.getCode());
    }
}
