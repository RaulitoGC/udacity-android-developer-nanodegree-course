package com.rguzman.popularmovie.di.component;

import android.app.Application;

import com.rguzman.popularmovie.MovieApplication;
import com.rguzman.popularmovie.di.module.ActivityBinder;
import com.rguzman.popularmovie.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBinder.class
})
public interface ApplicationComponent extends AndroidInjector<MovieApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(MovieApplication app);
}
