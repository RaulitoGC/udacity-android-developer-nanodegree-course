package com.rguzman.popularmovie.di.module;

import com.rguzman.popularmovie.di.ActivityScope;
import com.rguzman.popularmovie.presentation.detail.MovieDetailActivity;
import com.rguzman.popularmovie.presentation.list.MovieListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBinder {

    @ActivityScope
    @ContributesAndroidInjector(modules = MovieListModule.class)
    abstract MovieListActivity movieListActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = MovieDetailModule.class)
    abstract MovieDetailActivity movieDetailActivity();

}
