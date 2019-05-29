package com.rguzman.techstore.presentation.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rguzman.techstore.data.exception.EmptyListException;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.data.preferences.UserPrefs;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.GetProducts;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;
import com.rguzman.techstore.presentation.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

public class ProductListViewModel extends ViewModel {

    private MutableLiveData<List<Product>> productListLiveData;
    private SingleLiveEvent<ProductListState> productListState;

    private final GetProducts getProducts;

    @Inject
    UserPrefs userPrefs;

    @Inject
    public ProductListViewModel(GetProducts getProducts, MutableLiveData<List<Product>> productListLiveData, SingleLiveEvent<ProductListState> productListState) {
        this.getProducts = getProducts;
        this.productListLiveData = productListLiveData;
        this.productListState = productListState;
    }

    public MutableLiveData<List<Product>> getProductList() {
        return productListLiveData;
    }

    public SingleLiveEvent<ProductListState> getProductListState() {
        return productListState;
    }

    public void initializeProducts(String categoryId) {
        this.productListState.setValue(ProductListState.SHOW_LOADING);
        this.loadProducts(true, categoryId);
    }

    public void loadProducts(boolean forceCache, String categoryId) {
        if (!forceCache) {
            this.productListState.setValue(ProductListState.SHOW_REFRESH_LOADING);
        }
        this.getProducts.execute(forceCache, GetProducts.Parameters.getProductParameters(userPrefs.getUser().getToken(), categoryId),
                new UseCaseCallback<List<Product>>() {
                    @Override
                    public void onNetworkResponse(List<Product> data) {
                        productListLiveData.setValue(data);
                    }

                    @Override
                    public void onDiskResponse(List<Product> data) {
                        productListLiveData.setValue(data);
                    }

                    @Override
                    public void onError(Exception exception) {
                        showError(exception);
                    }
                });
    }

    private void showError(Exception exception) {

        if (exception instanceof EmptyListException) {
            this.productListState.setValue(ProductListState.EMPTY_LIST);
            return;
        }

        if (exception instanceof NetworkConnectionException) {
            this.productListState.setValue(ProductListState.NETWORK_CONNECTION_ERROR);
        } else if (exception instanceof GenericException) {
            this.productListState.setValue(ProductListState.GENERIC_ERROR);
        }
    }

}
