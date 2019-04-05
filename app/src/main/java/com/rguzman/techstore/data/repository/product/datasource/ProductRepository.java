package com.rguzman.techstore.data.repository.product.datasource;

import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.GetProducts;

import java.util.List;

public interface ProductRepository {

    void loadProducts(boolean forceCache, String token, String categoryId, GetProducts.Callback<List<Product>> callback);
}
