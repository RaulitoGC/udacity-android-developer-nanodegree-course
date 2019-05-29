package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Product;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

public class GetProduct extends UseCase<String, Product> {

    private final ProductRepository productRepository;

    @Inject
    public GetProduct(ProductRepository productRepository, @Named("uiExecutor") Executor uiExecutor) {
        super(uiExecutor);
        this.productRepository = productRepository;
    }

    @Override
    public void execute(String productId, UseCaseCallback<Product> callback) {
        this.productRepository.loadProduct(productId, new UseCaseCallback<Product>() {
            @Override
            public void onNetworkResponse(Product data) {
                callback.onNetworkResponse(data);
            }

            @Override
            public void onDiskResponse(Product data) {
                uiExecutor.execute(() -> callback.onDiskResponse(data));
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
