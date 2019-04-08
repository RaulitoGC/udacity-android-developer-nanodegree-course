package com.rguzman.techstore.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rguzman.techstore.di.key.ViewModelKey;
import com.rguzman.techstore.presentation.ViewModelFactory;
import com.rguzman.techstore.presentation.category.CategoryListViewModel;
import com.rguzman.techstore.presentation.login.LoginViewModel;
import com.rguzman.techstore.presentation.product.ProductListViewModel;
import com.rguzman.techstore.presentation.product.detail.ProductDetailViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryListViewModel.class)
    abstract ViewModel bindCategoryListViewModel(CategoryListViewModel categoryListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel.class)
    abstract ViewModel bindProductListViewModel(ProductListViewModel productListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel.class)
    abstract ViewModel bindProductDetailViewModel(ProductDetailViewModel productDetailViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
