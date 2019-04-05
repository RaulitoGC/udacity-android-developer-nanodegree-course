package com.rguzman.techstore.data.repository.product.datasource;

import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.GetFeatures;
import com.rguzman.techstore.domain.usecase.GetProduct;
import com.rguzman.techstore.domain.usecase.GetProducts;

import java.util.List;

public interface ProductRepository {

  void loadProducts(boolean forceCache, String token, String categoryId, GetProducts.Callback<List<Product>> callback);

  void loadProduct(String productId, GetProduct.Callback<Product> callback);

  void loadFeatures(String productId, GetFeatures.Callback<List<Feature>> callback);
}
