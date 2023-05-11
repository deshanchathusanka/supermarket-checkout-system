package org.cdl.service;

import org.cdl.object.PriceScheme;
import org.cdl.object.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SetupServiceImpl implements SetupService {
    private final Map<String, List<PriceScheme>> schemeMap;

    SetupServiceImpl(Map<String, List<PriceScheme>> schemeMap) {
        this.schemeMap = schemeMap;
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
}
