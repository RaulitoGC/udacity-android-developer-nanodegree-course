package com.rguzman.baking.di.module;

import com.rguzman.baking.presentation.step.RecipeStepFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecipeStepModule {
    @ContributesAndroidInjector
    abstract RecipeStepFragment recipeStepFragment();
}
