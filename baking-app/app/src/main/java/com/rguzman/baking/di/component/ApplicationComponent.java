package com.rguzman.baking.di.component;

import android.app.Application;

import com.rguzman.baking.BakingApplication;
import com.rguzman.baking.di.module.ActivityBinder;
import com.rguzman.baking.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBinder.class
})
public interface ApplicationComponent extends AndroidInjector<BakingApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(BakingApplication app);
}
