package com.rguzman.techstore.data.repository.product.datasource.disk;

import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public interface ProductDiskDataSource {

  LiveData<Product> loadProduct(String productId);

  void saveProduct(Product product);

  void saveFeatures(List<Feature> features);

  LiveData<List<Product>> loadProducts(String categoryId);

  LiveData<List<Feature>> loadFeatures(String productId);
}
