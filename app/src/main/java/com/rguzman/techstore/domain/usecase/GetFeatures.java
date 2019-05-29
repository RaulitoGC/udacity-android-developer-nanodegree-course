package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Feature;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

public class GetFeatures extends UseCase<String, List<Feature>> {

    private final ProductRepository productRepository;

    @Inject
    public GetFeatures(ProductRepository productRepository, @Named("uiExecutor") Executor uiExecutor) {
        super(uiExecutor);
        this.productRepository = productRepository;
    }

    @Override
    public void execute(boolean forceCache, String productId, UseCaseCallback<List<Feature>> callback) {
        this.productRepository.loadFeatures(productId, new UseCaseCallback<List<Feature>>() {
            @Override
            public void onNetworkResponse(List<Feature> data) {
                callback.onNetworkResponse(data);
            }

            @Override
            public void onDiskResponse(List<Feature> data) {
                uiExecutor.execute(() -> callback.onDiskResponse(data));

            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
