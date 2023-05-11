package org.cdl.service;

import org.cdl.object.BookingItem;
import org.cdl.object.ShoppingBasket;
import org.cdl.object.ShoppingBasketImpl;

/**
 * Cart Service Interface
 *
 * @author deshan
 * @since 1.0
 */
public interface CartService {

    /**
     * create new shopping basket
     *
     * @return shopping basket {@link ShoppingBasketImpl}
     */
    ShoppingBasket createShoppingBasket();

    /**
     * read existing shopping basket
     *
     * @param sessionId
     * @return shopping basket {@link ShoppingBasket}
     */
    ShoppingBasket readShoppingBasket(String sessionId);

    /**
     * add item to existing basket
     *
     * @param sessionId   session id
     * @param bookingItem booking item
     * @return shopping basket
     */
    ShoppingBasket addItem(String sessionId, BookingItem bookingItem);
}
