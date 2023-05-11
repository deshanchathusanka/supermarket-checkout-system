package org.cdl.object;

import java.util.Objects;

/**
 * Discount Price Scheme Class
 *
 * @author deshan
 * @since 1.0
 */
public class PriceScheme {
    private String schemeCode;
    private final Product product;
    private int quantity;
    private double price;

    public PriceScheme(Product product) {
        this.product = product;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceScheme that = (PriceScheme) o;
        return quantity == that.quantity && Double.compare(that.price, price) == 0 && Objects.equals(schemeCode, that.schemeCode) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schemeCode, product, quantity, price);
    }

    @Override
    public String toString() {
        return "PriceScheme{" +
                "schemeCode='" + schemeCode + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
