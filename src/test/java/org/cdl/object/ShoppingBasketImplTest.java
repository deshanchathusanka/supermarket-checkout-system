package org.cdl.object;

import org.cdl.service.SetupService;
import org.cdl.service.SetupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ShoppingBasketImplTest {
    private ShoppingBasket shoppingBasket;
    private SetupService setupService;

    @BeforeEach
    void setUp() {
        String sessionId = UUID.randomUUID().toString();
        setupService = Mockito.spy(new SetupServiceImpl(new HashMap<>()));
        shoppingBasket = Mockito.spy(new ShoppingBasketImpl(sessionId, new HashMap<>(), setupService));
    }

    @Test
    void addItemTest() {
        // mock
        Product product = new Product(Product.Codes.B.getCode());
        product.setUnitPrice(30);
        PriceScheme priceScheme1 = new PriceScheme(product);
        priceScheme1.setQuantity(1);
        priceScheme1.setPrice(30);
        PriceScheme priceScheme2 = new PriceScheme(product);
        priceScheme2.setQuantity(2);
        priceScheme2.setPrice(45);
        doReturn(List.of(priceScheme2, priceScheme1))
                .when(setupService)
                .readSchemes(Product.Codes.B.getCode());

        // method invocation
        BookingItem bookingItem = new BookingItem(product);
        bookingItem.addQuantity(3);
        shoppingBasket.addItem(bookingItem);

        // assertions
        assertThat(shoppingBasket.getTotalPrice()).isEqualTo(45 + 30);
        assertThat(shoppingBasket.getItem(Product.Codes.B.getCode()).getPrice()).isEqualTo(45 + 30);
    }
}