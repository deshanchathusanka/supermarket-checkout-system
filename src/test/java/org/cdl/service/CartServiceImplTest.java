package org.cdl.service;

import org.cdl.object.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    private CartService cartService;
    private SetupService setupService;

    @BeforeEach
    void setUp() {
        cartService = Mockito.spy(new CartServiceImpl(new HashMap<>()));
        setupService = Mockito.spy(new SetupServiceImpl(new HashMap<>()));
    }

    @Test
    void createShoppingBasketTest() {
        // method invocation
        ShoppingBasket shoppingBasket = cartService.createShoppingBasket();

        // assertions
        assertThat(shoppingBasket).isNotNull();
        assertThat(shoppingBasket.getSessionId()).isNotNull();
    }

    @Test
    void readShoppingBasketTest() {
        // method invocation
        ShoppingBasket shoppingBasket = cartService.createShoppingBasket();
        ShoppingBasket readShoppingBasket = cartService.readShoppingBasket(shoppingBasket.getSessionId());

        // assertions
        assertThat(readShoppingBasket).isNotNull();
        assertThat(readShoppingBasket.getSessionId()).isEqualTo(shoppingBasket.getSessionId());
    }

    @Test
    void addItemTest() {
        //mock
        Product product = new Product(Product.Codes.A.getCode());
        product.setUnitPrice(50);
        PriceScheme priceScheme1 = new PriceScheme(product);
        priceScheme1.setQuantity(1);
        priceScheme1.setPrice(50);
        doReturn(List.of(priceScheme1))
                .when(setupService)
                .readSchemes(Product.Codes.A.getCode());

        // method invocation
        BookingItem bookingItem = new BookingItem(product);
        bookingItem.addQuantity(2);

        String sessionId = UUID.randomUUID().toString();
        ShoppingBasket shoppingBasket = Mockito.spy(new ShoppingBasketImpl(sessionId, new HashMap<>(), setupService));
        cartService = Mockito.spy(new CartServiceImpl(Map.of(sessionId, shoppingBasket)));
        shoppingBasket = cartService.addItem(sessionId, bookingItem);

        // assertions
        BookingItem current = shoppingBasket.getItem(Product.Codes.A.getCode());
        assertThat(current.getQuantity()).isEqualTo(2);
        assertThat(current.getPrice()).isEqualTo(2 * 50);
        assertThat(shoppingBasket.getTotalPrice()).isEqualTo(2 * 50);
    }

}
