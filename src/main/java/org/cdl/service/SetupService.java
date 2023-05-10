package org.cdl.service;

import org.cdl.object.PriceScheme;

/**
 * @author deshan
 * @since 1.0
 */
public interface SetupService {

    /**
     * add price scheme
     *
     * @param priceScheme {@link PriceScheme}
     */
    void addScheme(PriceScheme priceScheme);
}
