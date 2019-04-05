package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Feature;

import java.util.List;

public class GetFeatures extends UseCase<String, List<Feature>> {

    private final ProductRepository productRepository;

    public GetFeatures(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(boolean forceCache, String params, Callback<List<Feature>> callback) {

    }
}
