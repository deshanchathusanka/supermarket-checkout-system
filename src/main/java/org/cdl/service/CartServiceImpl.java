package org.cdl.service;

import org.cdl.object.BookingItem;
import org.cdl.object.ShoppingBasket;
import org.cdl.object.ShoppingBasketImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author deshan
 * @since 1.0
 */
public class CartServiceImpl implements CartService {
    private static Map<String, ShoppingBasket> basketMap = new HashMap<>();

    @Override
    public ShoppingBasket createShoppingBasket() {
        String sessionId = UUID.randomUUID().toString();
        ShoppingBasket shoppingBasket = new ShoppingBasketImpl(sessionId);
        basketMap.put(sessionId, shoppingBasket);
        return shoppingBasket;
    }

    @Override
    public ShoppingBasket readShoppingBasket(String sessionId) {
        ShoppingBasket shoppingBasket = basketMap.get(sessionId);
        return shoppingBasket;
    }

    @Override
    public ShoppingBasket addItem(String sessionId, BookingItem bookingItem) {
        ShoppingBasket shoppingBasket = basketMap.get(sessionId);
        shoppingBasket.addItem(bookingItem);
        return shoppingBasket;
    }
}
