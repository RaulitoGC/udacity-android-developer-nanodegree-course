package com.rguzman.baking.data.repository.datasource.network;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.rguzman.baking.data.exception.GenericException;
import com.rguzman.baking.data.exception.NetworkConnectionException;
import com.rguzman.baking.data.net.ApiService;
import com.rguzman.baking.data.net.NetworkCallback;
import com.rguzman.baking.domain.model.Recipe;
import com.rguzman.baking.presentation.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeNetworkDataSource implements NetworkDataSource {

    private final ApiService apiService;
    private final Context context;

    @Inject
    public RecipeNetworkDataSource(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public void loadPopularMovies(NetworkCallback<List<Recipe>> callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {

            Call<List<Recipe>> call = apiService.loadRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        final MutableLiveData<List<Recipe>> liveData = new MutableLiveData<>();
                        liveData.setValue(response.body());
                        callback.onResponse(liveData);
                    } else {
                        callback.onError(new GenericException());
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    t.printStackTrace();
                    callback.onError(new GenericException());
                }
            });

        }
    }
}
