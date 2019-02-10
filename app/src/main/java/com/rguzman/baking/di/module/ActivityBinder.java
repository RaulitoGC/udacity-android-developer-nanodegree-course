package com.rguzman.baking.di.module;

import com.rguzman.baking.di.ActivityScope;
import com.rguzman.baking.presentation.detail.RecipeDetailActivity;
import com.rguzman.baking.presentation.detail.step.RecipeStepDetailActivity;
import com.rguzman.baking.presentation.recipe.RecipeListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBinder {

    @ActivityScope
    @ContributesAndroidInjector(modules = RecipeListModule.class)
    abstract RecipeListActivity recipeListActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = RecipeDetailModule.class)
    abstract RecipeDetailActivity recipeDetailActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = RecipeDetailModule.class)
    abstract RecipeStepDetailActivity recipeStepDetailActivity();

}
