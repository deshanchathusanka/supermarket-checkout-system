package org.cdl.service;

import org.cdl.object.ShoppingBasket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = Mockito.spy(new CartServiceImpl());
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

}
