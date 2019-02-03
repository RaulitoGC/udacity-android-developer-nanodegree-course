package com.rguzman.baking.di.module;

import com.rguzman.baking.di.ActivityScope;
import com.rguzman.baking.presentation.recipe.RecipeListActivity;
import com.rguzman.baking.presentation.step.RecipeStepActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBinder {

    @ActivityScope
    @ContributesAndroidInjector(modules = RecipeListModule.class)
    abstract RecipeListActivity movieListActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = RecipeStepModule.class)
    abstract RecipeStepActivity movieDetailActivity();

}
