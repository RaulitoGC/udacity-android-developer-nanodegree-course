package com.rguzman.techstore.data.repository.product.datasource;

import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.GetFeatures;
import com.rguzman.techstore.domain.usecase.GetProduct;
import com.rguzman.techstore.domain.usecase.GetProducts;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;

import java.util.List;

public interface ProductRepository {

  void loadProducts(boolean forceCache, String token, String categoryId,
                    UseCaseCallback<List<Product>> callback);

  void loadProduct(String productId, UseCaseCallback<Product> callback);

  void loadFeatures(String productId, UseCaseCallback<List<Feature>> callback);
}





