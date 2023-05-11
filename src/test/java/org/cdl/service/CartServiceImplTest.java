package org.cdl.service;

import org.cdl.object.BookingItem;
import org.cdl.object.Product;
import org.cdl.object.ShoppingBasket;
import org.cdl.object.ShoppingBasketImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

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
        // method invocation
        Product product = new Product(Product.Codes.A.getCode());
        BookingItem bookingItem = new BookingItem(product);
        bookingItem.addQuantity(2);

        String sessionId = UUID.randomUUID().toString();
        ShoppingBasket shoppingBasket = Mockito.spy(new ShoppingBasketImpl(sessionId, new HashMap<>(), setupService));
        doAnswer(invocationOnMock -> {
            BookingItem item = invocationOnMock.getArgument(0);
            // assertion
            assertThat(item).isEqualTo(bookingItem);
            return null;
        }).when(shoppingBasket).addItem(any(BookingItem.class));

        cartService = Mockito.spy(new CartServiceImpl(Map.of(sessionId, shoppingBasket)));
        cartService.addItem(sessionId, bookingItem);
    }

}
