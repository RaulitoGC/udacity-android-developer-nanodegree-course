package com.rguzman.techstore.data.repository.product.datasource.disk;

import com.rguzman.techstore.data.database.DataBaseCallback;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

public interface ProductDiskDataSource {

    void loadProduct(String productId, DataBaseCallback<Product> callback);

    void saveProduct(Product product);

    void saveFeatures(List<Feature> features);

    void loadProducts(String categoryId, DataBaseCallback<List<Product>> callback);

    void loadFeatures(String productId, DataBaseCallback<List<Feature>> callback);
}
