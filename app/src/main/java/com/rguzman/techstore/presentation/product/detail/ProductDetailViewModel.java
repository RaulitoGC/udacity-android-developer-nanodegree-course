package com.rguzman.techstore.presentation.product.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rguzman.techstore.R;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.preferences.UserPrefs;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.GetFeatures;
import com.rguzman.techstore.domain.usecase.GetProduct;
import com.rguzman.techstore.domain.usecase.UseCaseCallbackImpl;

import java.util.List;

import javax.inject.Inject;

public class ProductDetailViewModel extends ViewModel {

  private LiveData<Product> productLiveData;
  private LiveData<List<Feature>> featureListLiveData;
  private ProductObserver productObserver;
  private FeatureListObserver featureListObserver;

  private final GetProduct getProduct;
  private final GetFeatures getFeatures;
  private ProductDetailView view;
  @Inject
  UserPrefs userPrefs;

  @Inject
  public ProductDetailViewModel(GetProduct getProduct, GetFeatures getFeatures) {
    this.getProduct = getProduct;
    this.getFeatures = getFeatures;
  }

  public void setView(ProductDetailView view) {
    this.view = view;
  }

  public void init(String productId) {

    this.productObserver = new ProductObserver();
    this.featureListObserver = new FeatureListObserver();

    if (this.productLiveData != null && featureListLiveData != null) {
      this.productLiveData.observeForever(productObserver);
      this.featureListLiveData.observeForever(featureListObserver);
      return;
    }
    loadProduct(productId);
    loadFeatures(productId);
  }

  public void loadProduct(String productId) {
    this.getProduct.execute(productId,
            new UseCaseCallbackImpl<Product>() {

              @Override
              public void onDiskResponse(MutableLiveData<Product> liveData) {
                productLiveData = liveData;
                productLiveData.observeForever(productObserver);
              }

              @Override
              public void onError(Exception exception) {
                showError(exception);
              }
            });
  }

  public void loadFeatures(String productId) {

    this.getFeatures.execute(productId,
            new UseCaseCallbackImpl<List<Feature>>() {

              @Override
              public void onDiskResponse(MutableLiveData<List<Feature>> liveData) {
                featureListLiveData = liveData;
                featureListLiveData.observeForever(featureListObserver);
              }

              @Override
              public void onError(Exception exception) {
                showError(exception);
              }
            });
  }


  private void showError(Exception exception) {
    String message = exception.getMessage();

    if (exception instanceof GenericException) {
      message = view.context().getString(R.string.message_exception_generic);
    }

    view.showError(message);
  }


  private final class FeatureListObserver implements Observer<List<Feature>> {

    @Override
    public void onChanged(List<Feature> features) {
      view.loadFeatures(features);
      featureListLiveData.removeObserver(this);
    }
  }


  private final class ProductObserver implements Observer<Product> {

    @Override
    public void onChanged(Product product) {
      view.loadProduct(product);
      productLiveData.removeObserver(this);
    }
  }
}
