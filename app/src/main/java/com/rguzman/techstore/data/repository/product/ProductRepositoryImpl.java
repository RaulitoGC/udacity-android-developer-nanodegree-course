package com.rguzman.techstore.data.repository.product;

import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.data.net.response.ProductResponse;
import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.data.repository.product.datasource.disk.ProductDiskDataSource;
import com.rguzman.techstore.data.repository.product.datasource.network.ProductNetworkDataSource;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

@Singleton
public class ProductRepositoryImpl implements ProductRepository {

  private final ProductDiskDataSource productDiskDataSource;
  private final ProductNetworkDataSource productNetworkDataSource;

  @Inject
  public ProductRepositoryImpl(ProductDiskDataSource productDiskDataSource, ProductNetworkDataSource productNetworkDataSource) {
    this.productDiskDataSource = productDiskDataSource;
    this.productNetworkDataSource = productNetworkDataSource;
  }

  @Override
  public void loadProducts(boolean forceCache, String token, String categoryId, UseCase.Callback<List<Product>> callback) {
    if (forceCache) {
      callback.onDiskResponse(productDiskDataSource.loadProducts(categoryId));
    }

    this.productNetworkDataSource.loadProducts(token, categoryId, new NetworkCallback<List<ProductResponse>>() {
      @Override
      public void onResponse(LiveData<List<ProductResponse>> liveData) {

        final MutableLiveData<List<Product>> productLiveData = new MutableLiveData<>();

        final List<ProductResponse> productResponseList = liveData.getValue();

        if (productResponseList != null) {
          final List<Product> productList = new ArrayList<>();
          for (int i = 0; i < productResponseList.size(); i++) {

            ProductResponse productResponse = productResponseList.get(i);
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
          productLiveData.setValue(productList);
        }

        callback.onNetworkResponse(productLiveData);
      }

      @Override
      public void onError(Exception exception) {
        callback.onError(exception);
      }
    });
  }

  @Override
  public void loadProduct(String productId, UseCase.Callback<Product> callback) {
    callback.onDiskResponse(productDiskDataSource.loadProduct(productId));
  }

  @Override
  public void loadFeatures(String productId, UseCase.Callback<List<Feature>> callback) {
    callback.onDiskResponse(productDiskDataSource.loadFeatures(productId));
  }
}
