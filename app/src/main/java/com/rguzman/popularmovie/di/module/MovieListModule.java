package com.rguzman.popularmovie.di.module;

import com.rguzman.popularmovie.presentation.list.MovieListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MovieListModule {

    @ContributesAndroidInjector
    abstract MovieListFragment movieListFragment();

}
