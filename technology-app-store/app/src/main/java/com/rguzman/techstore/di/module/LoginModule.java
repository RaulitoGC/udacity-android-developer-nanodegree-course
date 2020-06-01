package com.rguzman.techstore.di.module;

import com.rguzman.techstore.presentation.login.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginModule {

    @ContributesAndroidInjector
    abstract LoginFragment loginFragment();
}
