package com.rguzman.techstore.data.repository.product.datasource.disk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rguzman.techstore.data.database.AppDatabase;
import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProductDiskDataSourceImpl implements ProductDiskDataSource {

    private final AppDatabase appDatabase;
    private final Executor diskExecutor;

    @Inject
    public ProductDiskDataSourceImpl(AppDatabase appDatabase, Executor diskExecutor) {
        this.appDatabase = appDatabase;
        this.diskExecutor = diskExecutor;
    }


    @Override
    public MutableLiveData<Product> loadProduct(String productId) {
        return this.appDatabase.productDao().loadProduct(productId);
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
    public MutableLiveData<List<Product>> loadProducts(String categoryId) {
        return this.appDatabase.productDao().loadProducts(categoryId);
    }

    @Override
    public MutableLiveData<List<Feature>> loadFeatures(String productId) {
        return this.appDatabase.featureDao().loadFeatures(productId);
    }
}
