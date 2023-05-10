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
    private final Map<String, BookingItem> bookingItemMap;

    public ShoppingBasketImpl(String sessionId, Map<String, BookingItem> bookingItemMap) {
        this.sessionId = sessionId;
        this.bookingItemMap = bookingItemMap;
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
        BookingItem currentItem = bookingItemMap.computeIfAbsent(productCode, k -> new BookingItem(product));
        currentItem.addQuantity(quantity);
        recalculatePrice();
    }

    /**
     * recalculate the price of the shopping basket
     */
    private void recalculatePrice() {
        totalPrice = bookingItemMap.values()
                .stream()
                .map(BookingItem::getPrice)
                .reduce(0.0, Double::sum);
    }

    @Override
    public BookingItem getItem(String productCode) {
        return bookingItemMap.get(productCode);
    }

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }

}
