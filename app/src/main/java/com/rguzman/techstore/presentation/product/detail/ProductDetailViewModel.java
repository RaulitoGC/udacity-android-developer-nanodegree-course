package com.rguzman.techstore.presentation.product.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.data.preferences.UserPrefs;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.GetFeatures;
import com.rguzman.techstore.domain.usecase.GetProduct;
import com.rguzman.techstore.domain.usecase.UseCaseCallbackImpl;
import com.rguzman.techstore.presentation.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

public class ProductDetailViewModel extends ViewModel {

    private MutableLiveData<Product> productLiveData;
    private MutableLiveData<List<Feature>> featureListLiveData;
    private SingleLiveEvent<ProductDetailState> productDetailState;

    private final GetProduct getProduct;
    private final GetFeatures getFeatures;

    @Inject
    UserPrefs userPrefs;

    @Inject
    public ProductDetailViewModel(GetProduct getProduct, GetFeatures getFeatures,
            MutableLiveData<Product> productLiveData, MutableLiveData<List<Feature>> featureListLiveData, SingleLiveEvent<ProductDetailState> productDetailState) {
        this.getProduct = getProduct;
        this.getFeatures = getFeatures;
        this.productLiveData = productLiveData;
        this.featureListLiveData = featureListLiveData;
        this.productDetailState = productDetailState;
    }

    public LiveData<Product> getProductLiveData() {
        return productLiveData;
    }

    public LiveData<List<Feature>> getFeatureListLiveData() {
        return featureListLiveData;
    }

    public SingleLiveEvent<ProductDetailState> getProductDetailState() {
        return productDetailState;
    }

    public void init(String productId) {
        loadProduct(productId);
        loadFeatures(productId);
    }

    public void loadProduct(String productId) {
        this.getProduct.execute(productId,
                new UseCaseCallbackImpl<Product>() {

                    @Override
                    public void onDiskResponse(Product data) {
                        productLiveData.setValue(data);
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
                    public void onDiskResponse(List<Feature> data) {
                        featureListLiveData.setValue(data);
                    }

                    @Override
                    public void onError(Exception exception) {
                        showError(exception);
                    }
                });
    }


    private void showError(Exception exception) {
        if (exception instanceof GenericException) {
            productDetailState.setValue(ProductDetailState.GENERIC_ERROR);
        } else if (exception instanceof NetworkConnectionException) {
            productDetailState.setValue(ProductDetailState.NETWORK_CONNECTION_ERROR);
        }
    }
}
