package com.rguzman.techstore.data.repository.product;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.UseCase;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {


    @Override
    public void loadProducts(boolean forceUpdate, String token, String categoryId, UseCase.Callback<List<Product>> callback) {

    }
}
