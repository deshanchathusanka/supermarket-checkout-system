package org.cdl.object;

import org.cdl.service.SetupService;
import org.cdl.service.SetupServiceImpl;
import org.cdl.util.Codes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

class ShoppingBasketImplTest {
    private static ShoppingBasket shoppingBasket;
    private static SetupService setupService;

    @BeforeAll
    static void setUp() {
        String sessionId = UUID.randomUUID().toString();
        setupService = Mockito.spy(new SetupServiceImpl(new HashMap<>()));
        shoppingBasket = Mockito.spy(new ShoppingBasketImpl(sessionId, new HashMap<>(), setupService));
        mockProductA();
        mockProductB();
    }

    private static void mockProductA() {
        // mock
        Product productA = new Product(Codes.A.getCode());
        productA.setUnitPrice(50);
        // scheme 1 : single quantity price scheme
        PriceScheme priceScheme1 = new PriceScheme(productA);
        priceScheme1.setQuantity(1);
        priceScheme1.setPrice(50);
        // scheme 2 : multiple quantity price scheme
        PriceScheme priceScheme2 = new PriceScheme(productA);
        priceScheme2.setQuantity(3);
        priceScheme2.setPrice(130);
        // scheme 3 : multiple quantity price scheme
        PriceScheme priceScheme3 = new PriceScheme(productA);
        priceScheme3.setQuantity(10);
        priceScheme3.setPrice(400);
        doReturn(List.of(priceScheme3, priceScheme2, priceScheme1))
                .when(setupService)
                .readSchemes(Codes.A.getCode());
    }

    private static void mockProductB() {
        // mock
        Product productB = new Product(Codes.B.getCode());
        productB.setUnitPrice(30);
        // scheme 1 : single quantity price scheme
        PriceScheme priceScheme1 = new PriceScheme(productB);
        priceScheme1.setQuantity(1);
        priceScheme1.setPrice(30);
        // scheme 2 : multiple quantity price scheme
        PriceScheme priceScheme2 = new PriceScheme(productB);
        priceScheme2.setQuantity(2);
        priceScheme2.setPrice(45);
        doReturn(List.of(priceScheme2, priceScheme1))
                .when(setupService)
                .readSchemes(Codes.B.getCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"A:2:100:100",
            "B:1:30:130",
            "A:1:130:160",
            "B:1:45:175",
            "A:3:260:305",
            "B:1:75:335",
            "A:4:400:475"}, delimiter = ':')
    void addItemTest(String productCode, int quantity, double totalItemPrice, double totalBasketPrice) {
        // method invocation
        Product product = new Product(productCode);
        BookingItem bookingItem = new BookingItem(product);
        bookingItem.addQuantity(quantity);
        shoppingBasket.addItem(bookingItem);

        // assertions
        assertThat(shoppingBasket.getTotalPrice()).isEqualTo(totalBasketPrice);
        assertThat(shoppingBasket.getItem(productCode).getPrice()).isEqualTo(totalItemPrice);
    }
}