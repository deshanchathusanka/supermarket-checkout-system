package org.cdl.object;

/**
 * @author deshan
 * @since 1.0
 */
public class ShoppingBasketImpl implements ShoppingBasket {
    private String sessionId;

    public ShoppingBasketImpl(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
