package org.cdl.service;

import org.cdl.object.PriceScheme;
import org.cdl.object.Product;

import java.util.*;
import java.util.stream.Collectors;

public class SetupServiceImpl implements SetupService {

    // TODO : Need to implement cache to keep price schemes
    private static Map<String, List<PriceScheme>> schemeMap = new HashMap<>();

    public SetupServiceImpl() {
    }

    /**
     * This constructor is only used for testing purposes
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
        return schemeMap.get(productCode);
    }
}
