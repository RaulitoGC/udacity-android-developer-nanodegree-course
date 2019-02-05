package com.rguzman.baking.presentation.recipe;

import android.content.Context;

import com.rguzman.baking.domain.model.Recipe;

import java.util.List;

public interface RecipeListView {

    void loadRecipes(List<Recipe> recipes);

    Context context();

    void showError(String message);
}
