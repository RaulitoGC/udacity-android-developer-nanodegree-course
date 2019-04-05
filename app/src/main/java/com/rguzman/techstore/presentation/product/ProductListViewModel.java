package com.rguzman.techstore.presentation.product;

import com.rguzman.techstore.R;
import com.rguzman.techstore.data.exception.EmptyListException;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.data.preferences.UserPrefs;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.domain.usecase.GetProducts;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ProductListViewModel extends ViewModel {

  private LiveData<List<Product>> productListLiveData;
  private ProductListObserver productListObserver;

  private final GetProducts getProducts;
  private ProductListView view;
  @Inject
  UserPrefs userPrefs;

  @Inject
  public ProductListViewModel(GetProducts getProducts) {
    this.getProducts = getProducts;
  }

  public void setView(ProductListView view) {
    this.view = view;
  }

  public void init(String categoryId) {
    this.productListObserver = new ProductListObserver();
    if (this.productListLiveData != null) {
      productListLiveData.observeForever(productListObserver);
      return;
    }
    initializeMovies(categoryId);
  }

  private void initializeMovies(String categoryId) {
    view.showLoading();
    loadProducts(true, categoryId);
  }

  public void loadProducts(boolean forceCache, String categoryId) {
    if (!forceCache) {
      this.view.showRefreshLoading();
    }
    this.getProducts.execute(forceCache, GetProducts.Parameters.getProductParameters(userPrefs.getUser().getToken(), categoryId),
            new GetProducts.Callback<List<Product>>() {

              @Override
              public void onNetworkResponse(LiveData<List<Product>> liveData) {
                productListLiveData = liveData;
                productListLiveData.observeForever(productListObserver);
              }

              @Override
              public void onDiskResponse(LiveData<List<Product>> liveData) {
                productListLiveData = liveData;
                productListLiveData.observeForever(productListObserver);
              }

              @Override
              public void onError(Exception exception) {
                showError(exception);
              }
            });
  }

  private void showError(Exception exception) {
    String message = exception.getMessage();

    if (exception instanceof EmptyListException) {
      message = view.context().getString(R.string.message_exception_empty_category_list);
      view.showEmptyList(message);
      return;
    }

    if (exception instanceof NetworkConnectionException) {
      message = view.context().getString(R.string.message_exception_network_connection);
    } else if (exception instanceof GenericException) {
      message = view.context().getString(R.string.message_exception_generic);
    }

    view.showError(message);
  }

  private final class ProductListObserver implements Observer<List<Product>> {

    @Override
    public void onChanged(List<Product> products) {
      view.hideLoading();
      view.hideRefreshLoading();
      if (view.isEmptyList()) {
        view.loadListWithAnimation(products);
      } else {
        view.loadList(products);
      }
      productListLiveData.removeObserver(this);
    }
  }

}
