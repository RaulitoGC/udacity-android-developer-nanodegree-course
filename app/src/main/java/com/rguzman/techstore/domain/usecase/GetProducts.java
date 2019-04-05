package com.rguzman.techstore.domain.usecase;

import androidx.lifecycle.LiveData;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

import javax.inject.Inject;

public class GetProducts extends UseCase<GetProducts.Parameters, List<Product>> {

    private final ProductRepository productRepository;

    @Inject
    public GetProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(boolean forceUpdate, Parameters params, Callback<List<Product>> callback) {
        this.setForceCache(forceUpdate);
        this.productRepository.loadProducts(isForceCache(), params.token, params.categoryId, new Callback<List<Product>>() {
            @Override
            public void onNetworkResponse(LiveData<List<Product>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onDiskResponse(LiveData<List<Product>> liveData) {
                callback.onDiskResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public static final class Parameters {

        private final String token;
        private final String categoryId;

        public Parameters(String token, String categoryId) {
            this.token = token;
            this.categoryId = categoryId;
        }

        public static Parameters getProductParameters(String token, String categoryId) {
            return new Parameters(token, categoryId);
        }
    }
}
