package com.rguzman.techstore.di.module;

import com.rguzman.techstore.presentation.product.detail.ProductDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProductDetailModule {

    @ContributesAndroidInjector
    abstract ProductDetailFragment productDetailFragment();
}
