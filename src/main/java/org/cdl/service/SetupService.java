package org.cdl.service;

import org.cdl.object.PriceScheme;

import java.util.List;

/**
 * Setup Service Interface
 *
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

    /**
     * retrieve price schemes
     *
     * @param productCode product code
     * @return list of price schemes for given product code {@link  PriceScheme}
     */
    List<PriceScheme> readSchemes(String productCode);
}
