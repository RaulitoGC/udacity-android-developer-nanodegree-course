package com.rguzman.techstore.di.component;

import com.rguzman.techstore.TechStoreApplication;
import com.rguzman.techstore.di.module.ActivityBinder;
import com.rguzman.techstore.di.module.ApplicationModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBinder.class})
public interface ApplicationComponent extends AndroidInjector<TechStoreApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<TechStoreApplication> {
    }
}
