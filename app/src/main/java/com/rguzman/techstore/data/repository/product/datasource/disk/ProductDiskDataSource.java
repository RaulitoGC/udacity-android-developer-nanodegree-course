package com.rguzman.techstore.data.repository.product.datasource.disk;

import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface ProductDiskDataSource {

  LiveData<Product> loadProduct(String productId);

  void saveProducts(List<Product> products);

  void saveFeatures(List<Feature> features);

  LiveData<List<Product>> loadProducts(String categoryId);

  LiveData<List<Feature>> loadFeatures(String productId);
}
