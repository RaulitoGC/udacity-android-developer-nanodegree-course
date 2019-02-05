package com.rguzman.baking.di.module;

import com.rguzman.baking.di.ActivityScope;
import com.rguzman.baking.presentation.detail.RecipeDetailActivity;
import com.rguzman.baking.presentation.recipe.RecipeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBinder {

    @ActivityScope
    @ContributesAndroidInjector(modules = RecipeListModule.class)
    abstract RecipeActivity recipeActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = RecipeDetailModule.class)
    abstract RecipeDetailActivity recipeDetailActivity();

}
