package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Product;

import javax.inject.Inject;

public class GetProduct extends UseCase<String, Product> {

    private final ProductRepository productRepository;

    @Inject
    public GetProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public void execute(boolean forceCache, String params, Callback<Product> callback) {

    }
}
