package org.cdl.object;

import java.util.Objects;

/**
 * Product Class
 *
 * @author deshan
 * @since 1.0
 */
public class Product {
    private final String code;
    private double unitPrice;

    public Product(String code) {
        this.code = code;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.unitPrice, unitPrice) == 0 && Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, unitPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
