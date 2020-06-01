package com.rguzman.techstore.data.repository.product.datasource.disk;

import com.rguzman.techstore.data.database.AppDatabase;
import com.rguzman.techstore.data.database.DataBaseCallback;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class ProductDiskDataSourceImpl implements ProductDiskDataSource {

    private final AppDatabase appDatabase;
    private final Executor diskExecutor;

    @Inject
    public ProductDiskDataSourceImpl(AppDatabase appDatabase, @Named("workExecutor") Executor diskExecutor) {
        this.appDatabase = appDatabase;
        this.diskExecutor = diskExecutor;
    }

    @Override
    public void loadProduct(String productId, DataBaseCallback<Product> callback) {
        diskExecutor.execute(() -> {
            Product product = this.appDatabase.productDao().getProductById(productId);
            if (product != null) {
                callback.onResponse(product);
            } else {
                callback.onError(new GenericException());
            }
        });
    }

    @Override
    public void saveProduct(Product product) {
        if (product != null) {
            diskExecutor.execute(() -> {
                Product localProduct = appDatabase.productDao().getProductById(product.getProductId());
                if (localProduct != null) {
                    product.setId(localProduct.getId());
                }
                this.appDatabase.productDao().insert(product);
            });
        }
    }

    @Override
    public void saveFeatures(List<Feature> features) {
        if (features != null) {
            diskExecutor.execute(() -> appDatabase.featureDao().insert(features));
        }
    }

    @Override
    public void loadProducts(String categoryId, DataBaseCallback<List<Product>> callback) {
        diskExecutor.execute(() -> {
            List<Product> list = appDatabase.productDao().loadProducts(categoryId);
            if (list != null) {
                callback.onResponse(list);
            } else {
                callback.onError(new GenericException());
            }
        });
    }

    @Override
    public void loadFeatures(String productId, DataBaseCallback<List<Feature>> callback) {
        diskExecutor.execute(() -> {
            List<Feature> list = appDatabase.featureDao().loadFeatures(productId);
            if (list != null) {
                callback.onResponse(list);
            } else {
                callback.onError(new GenericException());
            }
        });

    }
}
