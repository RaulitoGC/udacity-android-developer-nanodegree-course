package com.rguzman.baking.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rguzman.baking.di.key.ViewModelKey;
import com.rguzman.baking.presentation.ViewModelFactory;
import com.rguzman.baking.presentation.recipe.RecipeListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel.class)
    abstract ViewModel bindMovieListViewModel(RecipeListViewModel recipeListViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
