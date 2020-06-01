package com.rguzman.baking.data.repository;

import android.arch.lifecycle.LiveData;

import com.rguzman.baking.data.net.NetworkCallback;
import com.rguzman.baking.data.repository.datasource.RecipeDataSource;
import com.rguzman.baking.data.repository.datasource.network.NetworkDataSource;
import com.rguzman.baking.domain.model.Recipe;
import com.rguzman.baking.domain.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;

public class RecipeRepository implements RecipeDataSource {

    private final NetworkDataSource networkDataSource;

    @Inject
    public RecipeRepository(NetworkDataSource networkDataSource) {
        this.networkDataSource = networkDataSource;
    }

    @Override
    public void loadRecipes(UseCase.Callback<List<Recipe>> callback) {
        this.networkDataSource.loadPopularMovies(new NetworkCallback<List<Recipe>>() {
            @Override
            public void onResponse(LiveData<List<Recipe>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
