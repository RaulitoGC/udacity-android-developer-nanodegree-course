package com.rguzman.popularmovie.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rguzman.popularmovie.di.key.ViewModelKey;
import com.rguzman.popularmovie.presentation.detail.MovieDetailFragment;
import com.rguzman.popularmovie.presentation.list.MovieListViewModel;
import com.rguzman.popularmovie.presentation.list.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class MovieDetailModule {

    @ContributesAndroidInjector
    abstract MovieDetailFragment movieDetailFragment();

}
