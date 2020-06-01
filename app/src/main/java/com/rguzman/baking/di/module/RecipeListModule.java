package com.rguzman.baking.di.module;

import com.rguzman.baking.presentation.recipe.RecipeListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecipeListModule {

    @ContributesAndroidInjector
    abstract RecipeListFragment recipeListFragment();
}
