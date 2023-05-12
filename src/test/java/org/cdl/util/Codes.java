package org.cdl.util;

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