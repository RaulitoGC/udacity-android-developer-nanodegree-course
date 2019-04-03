package com.rguzman.techstore.di.module;

import com.rguzman.techstore.di.ActivityScope;
import com.rguzman.techstore.presentation.category.CategoryListActivity;
import com.rguzman.techstore.presentation.login.LoginActivity;
import com.rguzman.techstore.presentation.product.ProductListActivity;
import com.rguzman.techstore.presentation.product.detail.ProductDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBinder {

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = CategoryModule.class)
    abstract CategoryListActivity categoryListActivity();
}
