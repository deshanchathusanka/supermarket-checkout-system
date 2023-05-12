package org.cdl.object;

/**
 * Shopping Basket Interface
 *
 * @author deshan
 * @since 1.0
 */
public interface ShoppingBasket {

    /**
     * @return an unique session id
     */
    String getSessionId();

    /**
     * add item to basket
     *
     * @param bookingItem
     */
    void addItem(BookingItem bookingItem);

    /**
     * get item from the basket
     *
     * @param productCode product code
     * @return booking item {@link BookingItem}
     */
    BookingItem getItem(String productCode);

    /**
     * get total price of the basket
     *
     * @return
     */
    double getTotalPrice();
}
