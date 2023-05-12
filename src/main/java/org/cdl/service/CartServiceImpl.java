package org.cdl.service;

import org.cdl.object.BookingItem;
import org.cdl.object.ShoppingBasket;
import org.cdl.object.ShoppingBasketImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of Cart Service
 *
 * @author deshan
 * @since 1.0
 */
public class CartServiceImpl implements CartService {

    // TODO : Need to implement cache to keep shopping basket as memento object
    private static Map<String, ShoppingBasket> basketMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    public CartServiceImpl() {
        /* default constructor */
    }

    /**
     * This constructor is used only for testing purposes
     *
     * @param basketMap
     */
    CartServiceImpl(Map<String, ShoppingBasket> basketMap) {
        CartServiceImpl.basketMap = basketMap;
    }

    @Override
    public ShoppingBasket createShoppingBasket() {
        String sessionId = UUID.randomUUID().toString();
        ShoppingBasket shoppingBasket = new ShoppingBasketImpl(sessionId, new HashMap<>(), new SetupServiceImpl());
        basketMap.put(sessionId, shoppingBasket);
        return shoppingBasket;
    }

    @Override
    public ShoppingBasket readShoppingBasket(String sessionId) {
        Optional<ShoppingBasket> shoppingBasketOptional = Optional.ofNullable(basketMap.get(sessionId));
        if (shoppingBasketOptional.isEmpty()) {
            logger.info("Shopping Basket is not available. Please check the session Id!!!");
            return null;
        } else {
            return shoppingBasketOptional.get();
        }
    }

    @Override
    public ShoppingBasket addItem(String sessionId, BookingItem bookingItem) {
        Optional<ShoppingBasket> shoppingBasketOptional = Optional.ofNullable(basketMap.get(sessionId));
        if (shoppingBasketOptional.isEmpty()) {
            logger.info("Shopping Basket is not available. Please check the session Id!!!");
            return null;
        } else {
            ShoppingBasket shoppingBasket = shoppingBasketOptional.get();
            shoppingBasket.addItem(bookingItem);
            return shoppingBasket;
        }
    }
}
