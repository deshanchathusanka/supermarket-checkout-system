package org.cdl.service;

import org.cdl.object.BookingItem;
import org.cdl.object.Product;
import org.cdl.object.ShoppingBasket;
import org.cdl.object.ShoppingBasketImpl;
import org.cdl.util.Codes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;

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
    void readShoppingBasketTest_unavailable() {
        // method invocation
        String sessionId = UUID.randomUUID().toString();
        ShoppingBasket readShoppingBasket = cartService.readShoppingBasket(sessionId);

        // assertions
        assertThat(readShoppingBasket).isNull();
    }

    @Test
    void addItemTest() {
        // method invocation
        Product product = new Product(Codes.A.getCode());
        BookingItem bookingItem = new BookingItem(product);
        bookingItem.addQuantity(2);

        String sessionId = UUID.randomUUID().toString();
        ShoppingBasket shoppingBasket = Mockito.spy(new ShoppingBasketImpl(sessionId, new HashMap<>(), setupService));
        doNothing().when(shoppingBasket).addItem(any(BookingItem.class));

        cartService = Mockito.spy(new CartServiceImpl(Map.of(sessionId, shoppingBasket)));
        ShoppingBasket returned = cartService.addItem(sessionId, bookingItem);

        // assertions
        assertThat(returned).isNotNull();
    }

    @Test
    void addItemTest_unavailable_basket() {
        // method invocation
        Product product = new Product(Codes.A.getCode());
        BookingItem bookingItem = new BookingItem(product);
        bookingItem.addQuantity(2);
        String sessionId = UUID.randomUUID().toString();
        ShoppingBasket returned = cartService.addItem(sessionId, bookingItem);

        // assertions
        assertThat(returned).isNull();
    }

}
