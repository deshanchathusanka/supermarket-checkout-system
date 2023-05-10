package org.cdl.object;

/**
 * @author deshan
 * @since 1.0
 */
public class BookingItem {
    private final Product product;
    private int quantity;
    private double price;

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
        recalculatePrice();
    }

    /**
     * recalculate price based on unit price and discount price schemes
     */
    private void recalculatePrice() {
        // TODO : need to consider price schemes to recalculate prices
        double unitPrice = product.getUnitPrice();
        price = quantity * unitPrice;
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
}
