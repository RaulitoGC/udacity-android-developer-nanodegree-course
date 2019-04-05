package com.rguzman.techstore.data.repository.product.datasource.disk;

import androidx.lifecycle.LiveData;

import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

public interface ProductDiskDataSource {

    void saveProducts(List<Product> products);

    void saveFeatures(List<Feature> features);

    LiveData<List<Product>> loadProducts(String categoryId);

    LiveData<List<Feature>> loadFeatures(String productId);
}
