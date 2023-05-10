package org.cdl.service;

import org.cdl.object.PriceScheme;
import org.cdl.object.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetupServiceImpl implements SetupService {
    private final Map<String, List<PriceScheme>> schemeMap;

    SetupServiceImpl(Map<String, List<PriceScheme>> schemeMap) {
        this.schemeMap = schemeMap;
    }

    @Override
    public void addScheme(PriceScheme priceScheme) {
        Product product = priceScheme.getProduct();
        List<PriceScheme> priceSchemes = schemeMap.computeIfAbsent(product.getCode(), k -> new ArrayList<>());
        priceSchemes.add(priceScheme);
    }
}
