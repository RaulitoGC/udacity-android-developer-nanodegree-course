package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.product.datasource.ProductRepository;
import com.rguzman.techstore.domain.model.Feature;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class GetFeatures extends UseCase<String, List<Feature>> {

  private final ProductRepository productRepository;

  @Inject
  public GetFeatures(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void execute(boolean forceCache, String productId, Callback<List<Feature>> callback) {
    this.productRepository.loadFeatures(productId, new Callback<List<Feature>>() {
      @Override
      public void onNetworkResponse(LiveData<List<Feature>> liveData) {
        callback.onNetworkResponse(liveData);
      }

      @Override
      public void onDiskResponse(LiveData<List<Feature>> liveData) {
        callback.onDiskResponse(liveData);
      }

      @Override
      public void onError(Exception exception) {
        callback.onError(exception);
      }
    });
  }
}
