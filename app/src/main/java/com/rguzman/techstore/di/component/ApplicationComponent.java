package com.rguzman.techstore.di.component;

import android.app.Application;

import com.rguzman.techstore.TechStoreApplication;
import com.rguzman.techstore.di.module.ActivityBinder;
import com.rguzman.techstore.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBinder.class})
public interface ApplicationComponent extends AndroidInjector<TechStoreApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(TechStoreApplication app);
}
