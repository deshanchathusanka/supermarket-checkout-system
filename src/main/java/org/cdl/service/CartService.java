package org.cdl.service;

import org.cdl.object.ShoppingBasket;
import org.cdl.object.ShoppingBasketImpl;

/**
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
}
