package com.rguzman.techstore.di.module;

import com.rguzman.techstore.presentation.category.CategoryListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CategoryModule {

    @ContributesAndroidInjector
    abstract CategoryListFragment categoryListFragment();
}
