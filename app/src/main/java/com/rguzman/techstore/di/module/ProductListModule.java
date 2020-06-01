package com.rguzman.techstore.di.module;

import com.rguzman.techstore.presentation.product.ProductListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProductListModule {

    @ContributesAndroidInjector
    abstract ProductListFragment productListFragment();
}
