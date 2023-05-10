package org.cdl.object;

/**
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
}
