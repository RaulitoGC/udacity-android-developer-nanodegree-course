package com.rguzman.popularmovie.di.module;

import com.rguzman.popularmovie.presentation.detail.MovieDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MovieDetailModule {

    @ContributesAndroidInjector
    abstract MovieDetailFragment movieDetailFragment();

}
