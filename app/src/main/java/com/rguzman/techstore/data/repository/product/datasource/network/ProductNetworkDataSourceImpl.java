package com.rguzman.techstore.data.repository.product.datasource.network;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rguzman.techstore.data.exception.EmptyListException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.data.net.ApiService;
import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.data.net.response.ProductResponse;
import com.rguzman.techstore.presentation.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ProductNetworkDataSourceImpl implements ProductNetworkDataSource {

  private final ApiService apiService;
  private final Context context;

  @Inject
  public ProductNetworkDataSourceImpl(ApiService apiService, Context context) {
    this.apiService = apiService;
    this.context = context;
  }


  @Override
  public void loadProducts(String token, String categoryId, NetworkCallback<List<ProductResponse>> callback) {
    if (!NetworkUtils.isThereNetworkConnection(context)) {
      callback.onError(new NetworkConnectionException());
    } else {
      Call<List<ProductResponse>> call = apiService.loadProducts(token, categoryId);
      call.enqueue(new Callback<List<ProductResponse>>() {
        @Override
        public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
          if (response.isSuccessful() && response.body() != null) {
            callback.onResponse(response.body());
          } else {
            callback.onError(new EmptyListException());
          }
        }

        @Override
        public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
          t.printStackTrace();
          callback.onError(new EmptyListException());
        }
      });
    }
  }
}
