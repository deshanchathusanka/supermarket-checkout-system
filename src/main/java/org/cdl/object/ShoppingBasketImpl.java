package org.cdl.object;

import org.cdl.service.SetupService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Implementation of Shopping Basket
 *
 * @author deshan
 * @since 1.0
 */
public class ShoppingBasketImpl implements ShoppingBasket {
    private final String sessionId;
    private double totalPrice;
    private final Map<String, BookingItem> bookingItemMap;
    private final SetupService setupService;

    public ShoppingBasketImpl(String sessionId, Map<String, BookingItem> bookingItemMap, SetupService setupService) {
        this.sessionId = sessionId;
        this.bookingItemMap = bookingItemMap;
        this.setupService = setupService;
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
        List<PriceScheme> priceSchemes = setupService.readSchemes(productCode);

        Function<String, BookingItem> mapFunction = key -> {
            BookingItem item = new BookingItem(product);
            item.setPriceSchemes(priceSchemes);
            return item;
        };
        BookingItem currentItem = bookingItemMap.computeIfAbsent(productCode, mapFunction);
        currentItem.addQuantity(quantity);
        currentItem.recalculateItemPrice();
        recalculateBasketPrice();
    }

    /**
     * recalculate the price of the shopping basket
     */
    private void recalculateBasketPrice() {
        totalPrice = bookingItemMap.values()
                .stream()
                .map(BookingItem::getPrice)
                .reduce(0.00, Double::sum);
    }

    @Override
    public BookingItem getItem(String productCode) {
        return bookingItemMap.get(productCode);
    }

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingBasketImpl that = (ShoppingBasketImpl) o;
        return Double.compare(that.totalPrice, totalPrice) == 0 && Objects.equals(sessionId, that.sessionId) && Objects.equals(bookingItemMap, that.bookingItemMap) && Objects.equals(setupService, that.setupService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, totalPrice, bookingItemMap, setupService);
    }

    @Override
    public String toString() {
        return "ShoppingBasketImpl{" +
                "sessionId='" + sessionId + '\'' +
                ", totalPrice=" + totalPrice +
                ", bookingItemMap=" + bookingItemMap +
                ", setupService=" + setupService +
                '}';
    }
}
