package com.rguzman.baking.data.repository.datasource;

import com.rguzman.baking.domain.model.Recipe;
import com.rguzman.baking.domain.usecase.GetRecipes;

import java.util.List;

public interface RecipeDataSource {

    void loadRecipes(GetRecipes.Callback<List<Recipe>> callback);
}
