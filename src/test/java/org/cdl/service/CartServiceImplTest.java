package org.cdl.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(CartServiceImpl.class)
public class CartServiceImplTest {
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = Mockito.spy(new CartServiceImpl());
    }

    @Test
    void createShoppingBasketTest() {
        // method invocation
        ShoppingBasket shoppingBasket= cartService.createShoppingBasket();

        // assertions
        assertThat(shoppingBasket).isNotNull();
        assertThat(shoppingBasket.getSessionId()).isNotNull();
    }

}
