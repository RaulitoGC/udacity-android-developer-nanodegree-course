package com.rguzman.techstore.data.repository.category.datasource.network;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.data.net.ApiService;
import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.presentation.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CategoryNetworkDataSourceImpl implements CategoryNetworkDataSource {

    private final ApiService apiService;
    private final Context context;

    @Inject
    public CategoryNetworkDataSourceImpl(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public void loadCategories(String token, NetworkCallback<List<Category>> callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {

            Call<List<Category>> call = apiService.loadCategories(token);
            call.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        final MutableLiveData<List<Category>> liveData = new MutableLiveData<>();
                        liveData.setValue(response.body());
                        callback.onResponse(liveData);
                    } else {
                        callback.onError(new GenericException());
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    t.printStackTrace();
                    callback.onError(new GenericException());
                }
            });

        }
    }
}
