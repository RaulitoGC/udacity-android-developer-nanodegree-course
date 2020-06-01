package com.rguzman.baking.domain.usecase;

import android.arch.lifecycle.LiveData;

import com.rguzman.baking.data.repository.RecipeRepository;
import com.rguzman.baking.domain.model.Recipe;

import java.util.List;

import javax.inject.Inject;

public class GetRecipes extends UseCase<Void, List<Recipe>> {

    private final RecipeRepository recipeRepository;

    @Inject
    public GetRecipes(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void execute(Void params, Callback<List<Recipe>> callback) {
        this.recipeRepository.loadRecipes(new Callback<List<Recipe>>() {
            @Override
            public void onNetworkResponse(LiveData<List<Recipe>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
