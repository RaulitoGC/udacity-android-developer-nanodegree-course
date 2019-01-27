package com.rguzman.popularmovie.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rguzman.popularmovie.di.key.ViewModelKey;
import com.rguzman.popularmovie.presentation.list.MovieListViewModel;
import com.rguzman.popularmovie.presentation.list.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    abstract ViewModel bindUserProfileViewModel(MovieListViewModel movieListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
