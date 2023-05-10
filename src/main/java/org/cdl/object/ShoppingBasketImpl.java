package org.cdl.object;

import java.util.HashMap;
import java.util.Map;

/**
 * @author deshan
 * @since 1.0
 */
public class ShoppingBasketImpl implements ShoppingBasket {
    private String sessionId;
    private double totalPrice;
    private Map<String, BookingItem> bookingItems = new HashMap<>();

    public ShoppingBasketImpl(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void addItem(BookingItem bookingItem) {
        Product product = bookingItem.getProduct();
        String productCode = product.getCode();
        int quantity = bookingItem.getQuantity();
        BookingItem currentItem = bookingItems.get(productCode);
        if (currentItem == null) {
            currentItem = new BookingItem(product);
            bookingItems.put(productCode, currentItem);
        }
        currentItem.addQuantity(quantity);
        recalculatePrice();
    }

    /**
     * recalculate the price of the shopping basket
     */
    private void recalculatePrice() {
        totalPrice = bookingItems.values()
                .stream()
                .map(BookingItem::getPrice)
                .reduce(0.0, Double::sum);
    }

    @Override
    public BookingItem getItem(String productCode) {
        return bookingItems.get(productCode);
    }

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }

}
