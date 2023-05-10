package org.cdl.object;

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

    public enum Codes {
        A("A"), B("B"), C("C");
        private final String code;

        Codes(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
