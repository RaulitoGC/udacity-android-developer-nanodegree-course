package com.rguzman.techstore.data.repository.product;

import com.rguzman.techstore.data.database.DataBaseCallback;
import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.data.net.response.ProductResponse;
import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.data.repository.product.datasource.disk.ProductDiskDataSource;
import com.rguzman.techstore.data.repository.product.datasource.network.ProductNetworkDataSource;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductDiskDataSource productDiskDataSource;
    private final ProductNetworkDataSource productNetworkDataSource;

    @Inject
    public ProductRepositoryImpl(ProductDiskDataSource productDiskDataSource,
            ProductNetworkDataSource productNetworkDataSource) {
        this.productDiskDataSource = productDiskDataSource;
        this.productNetworkDataSource = productNetworkDataSource;
    }

    @Override
    public void loadProducts(boolean forceCache, String token, String categoryId,
            UseCaseCallback<List<Product>> callback) {
        if (forceCache) {
            productDiskDataSource.loadProducts(categoryId, new DataBaseCallback<List<Product>>() {
                @Override
                public void onResponse(List<Product> data) {
                    callback.onDiskResponse(data);
                }

                @Override
                public void onError(Exception exception) {
                    callback.onError(exception);
                }
            });

        }

        this.productNetworkDataSource.loadProducts(token, categoryId, new NetworkCallback<List<ProductResponse>>() {
            @Override
            public void onResponse(List<ProductResponse> data) {

                final List<Product> productList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {

                    ProductResponse productResponse = data.get(i);
                    Product product = new Product();

                    product.setName(productResponse.getName());
                    product.setProductId(productResponse.getProductId());
                    product.setCategoryId(productResponse.getCategoryId());
                    product.setDescription(productResponse.getDescription());
                    product.setImage(productResponse.getImage());
                    product.setPrice(productResponse.getPrice());

                    productList.add(product);
                    productDiskDataSource.saveProduct(product);
                    productDiskDataSource.saveFeatures(productResponse.getFeatures());
                }

                callback.onNetworkResponse(productList);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }

    @Override
    public void loadProduct(String productId, UseCaseCallback<Product> callback) {
        productDiskDataSource.loadProduct(productId, new DataBaseCallback<Product>() {
            @Override
            public void onResponse(Product data) {
                callback.onDiskResponse(data);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });

    }

    @Override
    public void loadFeatures(String productId, UseCaseCallback<List<Feature>> callback) {
        productDiskDataSource.loadFeatures(productId, new DataBaseCallback<List<Feature>>() {
            @Override
            public void onResponse(List<Feature> data) {
                callback.onDiskResponse(data);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
