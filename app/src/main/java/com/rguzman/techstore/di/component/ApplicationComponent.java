package com.rguzman.techstore.di.component;

import android.app.Application;

import com.rguzman.techstore.TechStoreApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

public interface ApplicationComponent extends AndroidInjector<TechStoreApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(TechStoreApplication app);

}
