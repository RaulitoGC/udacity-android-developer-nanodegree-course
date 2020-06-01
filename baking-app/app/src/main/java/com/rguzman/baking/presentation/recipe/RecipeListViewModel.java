package com.rguzman.baking.presentation.recipe;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.rguzman.baking.R;
import com.rguzman.baking.data.exception.GenericException;
import com.rguzman.baking.data.exception.NetworkConnectionException;
import com.rguzman.baking.domain.model.Recipe;
import com.rguzman.baking.domain.usecase.GetRecipes;
import com.rguzman.baking.presentation.IdlingResource.SimpleIdlingResource;

import java.util.List;

import javax.inject.Inject;

public class RecipeListViewModel extends ViewModel {

    private RecipeListView view;
    private final GetRecipes getRecipes;
    private LiveData<List<Recipe>> recipeListLiveData;
    private RecipeListObserver recipeListObserver;

    @Inject
    public RecipeListViewModel(GetRecipes getRecipes) {
        this.getRecipes = getRecipes;
    }

    public void setView(RecipeListView view) {
        this.view = view;
    }

    public void init(@Nullable final SimpleIdlingResource idlingResource) {

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        this.recipeListObserver = new RecipeListObserver(idlingResource);
        if (this.recipeListLiveData != null) {
            recipeListLiveData.observeForever(recipeListObserver);
            return;
        }
        initializeRecipes();
    }

    private void initializeRecipes() {
        this.getRecipes.execute(new GetRecipes.Callback<List<Recipe>>() {
            @Override
            public void onNetworkResponse(LiveData<List<Recipe>> liveData) {
                recipeListLiveData = liveData;
                recipeListLiveData.observeForever(recipeListObserver);
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
    }

    private void showError(Exception exception) {
        String message = exception.getMessage();
        if (exception instanceof NetworkConnectionException) {
            message = view.context().getString(R.string.message_exception_network_connection);
        } else if (exception instanceof GenericException) {
            message = view.context().getString(R.string.message_exception_generic);
        }

        view.showError(message);
    }


    private final class RecipeListObserver implements Observer<List<Recipe>> {

        @Nullable
        private final SimpleIdlingResource idlingResource;

        public RecipeListObserver(@Nullable SimpleIdlingResource idlingResource) {
            this.idlingResource = idlingResource;
        }

        @Override
        public void onChanged(@Nullable List<Recipe> recipes) {
            view.loadRecipes(recipes);
            recipeListLiveData.removeObserver(this);
            if (idlingResource != null) {
                idlingResource.setIdleState(true);
            }
        }
    }
}
