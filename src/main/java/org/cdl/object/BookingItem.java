package org.cdl.object;

import java.util.List;

/**
 * @author deshan
 * @since 1.0
 */
public class BookingItem {
    private final Product product;
    private int quantity;
    private double price;

    private List<PriceScheme> priceSchemes;

    public BookingItem(Product product) {
        this.product = product;
    }

    /**
     * add quantity
     *
     * @param count number of items
     */
    public void addQuantity(int count) {
        quantity += count;
    }

    /**
     * recalculate price based on unit price and discount price schemes
     */
    public void recalculateItemPrice() {
        double tempPrice = 0;
        int balanceQuantity = quantity;
        for (PriceScheme scheme : priceSchemes) {
            if (balanceQuantity >= scheme.getQuantity()) {
                tempPrice += (balanceQuantity / scheme.getQuantity()) * scheme.getPrice();
                balanceQuantity = balanceQuantity % scheme.getQuantity();
            }
        }
        price = tempPrice;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public List<PriceScheme> getPriceSchemes() {
        return priceSchemes;
    }

    public void setPriceSchemes(List<PriceScheme> priceSchemes) {
        this.priceSchemes = priceSchemes;
    }
}
