package org.cdl.service;

import org.cdl.object.PriceScheme;
import org.cdl.object.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Setup Service
 *
 * @author deshan
 * @since 1.0
 */
public class SetupServiceImpl implements SetupService {

    // TODO : Need to implement cache to keep price schemes
    private static Map<String, List<PriceScheme>> schemeMap = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(SetupServiceImpl.class);

    public SetupServiceImpl() {
        /* default constructor */
    }

    /**
     * This constructor is only used for testing purposes
     *
     * @param schemeMap
     */
    public SetupServiceImpl(Map<String, List<PriceScheme>> schemeMap) {
        SetupServiceImpl.schemeMap = schemeMap;
    }

    @Override
    public void addScheme(PriceScheme priceScheme) {
        Product product = priceScheme.getProduct();
        String productCode = product.getCode();
        List<PriceScheme> priceSchemes = schemeMap.computeIfAbsent(productCode, k -> new ArrayList<>());
        priceSchemes.add(priceScheme);
        Comparator<PriceScheme> descending = (ps1, ps2) -> ps2.getQuantity() - ps1.getQuantity();
        List<PriceScheme> sortedSchemes = priceSchemes.stream()
                .sorted(descending)
                .collect(Collectors.toList());
        schemeMap.put(product.getCode(), sortedSchemes);

    }

    @Override
    public List<PriceScheme> readSchemes(String productCode) {
        Optional<List<PriceScheme>> priceSchemeOpt = Optional.ofNullable(schemeMap.get(productCode));
        if (priceSchemeOpt.isEmpty()) {
            logger.info(String.format("No scheme has been setup for the product : %s", productCode));
            return Collections.emptyList();
        } else {
            return priceSchemeOpt.get();
        }
    }
}
