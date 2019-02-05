package com.rguzman.baking.di.module;

import com.rguzman.baking.presentation.detail.step.RecipeStepDetailFragment;
import com.rguzman.baking.presentation.detail.RecipeStepFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecipeDetailModule {

    @ContributesAndroidInjector
    abstract RecipeStepFragment recipeStepFragment();

    @ContributesAndroidInjector
    abstract RecipeStepDetailFragment recipeStepDetailFragment();
}
