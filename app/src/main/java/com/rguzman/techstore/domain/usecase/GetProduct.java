package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Product;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class GetProduct extends UseCase<String, Product> {

  private final ProductRepository productRepository;

  @Inject
  public GetProduct(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void execute(boolean forceCache, String params, Callback<Product> callback) {

  }

  @Override
  public void execute(String productId, Callback<Product> callback) {
    this.productRepository.loadProduct(productId, new Callback<Product>() {
      @Override
      public void onNetworkResponse(LiveData<Product> liveData) {
        callback.onNetworkResponse(liveData);
      }

      @Override
      public void onDiskResponse(LiveData<Product> liveData) {
        callback.onDiskResponse(liveData);
      }

      @Override
      public void onError(Exception exception) {
        callback.onError(exception);
      }
    });
  }
}
