package com.rguzman.techstore.data.repository.product.datasource.disk;

import androidx.lifecycle.LiveData;

import com.rguzman.techstore.data.database.AppDatabase;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

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
    public void saveProducts(List<Product> products) {

    }

    @Override
    public void saveFeatures(List<Feature> features) {

    }

    @Override
    public LiveData<List<Product>> loadProducts(String categoryId) {
        return this.appDatabase.productDao().loadProducts(categoryId);
    }

    @Override
    public LiveData<List<Feature>> loadFeatures(String productId) {
        return this.appDatabase.featureDao().loadFeatures(productId);
    }
}
