package org.cdl.service;

import org.cdl.object.ShoppingBasket;
import org.cdl.object.Product;
import org.cdl.object.BookingItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = Mockito.spy(new CartServiceImpl(new HashMap<>()));
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
        // method invocation
        Product product = new Product(Product.Codes.A.getCode());
        product.setUnitPrice(50);
        BookingItem bookingItem = new BookingItem(product);
        bookingItem.addQuantity(2);

        ShoppingBasket shoppingBasket = cartService.createShoppingBasket();
        shoppingBasket = cartService.addItem(shoppingBasket.getSessionId(), bookingItem);

        // assertions
        BookingItem current = shoppingBasket.getItem(Product.Codes.A.getCode());
        assertThat(current.getQuantity()).isEqualTo(2);
        assertThat(current.getPrice()).isEqualTo(2 * 50);
        assertThat(shoppingBasket.getTotalPrice()).isEqualTo(2 * 50);
    }

}
