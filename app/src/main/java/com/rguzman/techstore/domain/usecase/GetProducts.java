package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

public class GetProducts extends UseCase<GetProducts.Parameters, List<Product>> {

    private final ProductRepository productRepository;

    @Inject
    public GetProducts(ProductRepository productRepository, @Named("uiExecutor") Executor uiExecutor) {
        super(uiExecutor);
        this.productRepository = productRepository;
    }

    @Override
    public void execute(boolean forceUpdate, Parameters params, UseCaseCallback<List<Product>> callback) {
        this.setForceCache(forceUpdate);
        this.productRepository.loadProducts(isForceCache(), params.token, params.categoryId, new UseCaseCallback<List<Product>>() {
            @Override
            public void onNetworkResponse(List<Product> data) {
                callback.onNetworkResponse(data);
            }

            @Override
            public void onDiskResponse(List<Product> data) {
                uiExecutor.execute(() -> callback.onDiskResponse(data));
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
