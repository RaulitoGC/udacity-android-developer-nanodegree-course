package com.rguzman.popularmovie.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rguzman.popularmovie.data.database.AppDatabase;
import com.rguzman.popularmovie.data.net.ApiService;
import com.rguzman.popularmovie.data.net.serializer.MovieTypeAdapterSerializer;
import com.rguzman.popularmovie.data.repository.MovieRepository;
import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.data.repository.datasource.disk.DiskDataSource;
import com.rguzman.popularmovie.data.repository.datasource.disk.MovieDiskDataSource;
import com.rguzman.popularmovie.data.repository.datasource.network.MovieNetworkDataSource;
import com.rguzman.popularmovie.data.repository.datasource.network.NetworkDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new MovieTypeAdapterSerializer())
                .create();
    }

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging =
                new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(logging);
        return builder.build();
    }

    @Provides
    @Singleton
    ApiService getApiService(OkHttpClient okHttpClient, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "movie.db")
                .build();
    }

    @Provides
    @Singleton
    DiskDataSource provideUserDiskDataSource(MovieDiskDataSource movieDiskDataSource) {
        return movieDiskDataSource;
    }

    @Provides
    @Singleton
    NetworkDataSource provideUserNetworkDataSource(MovieNetworkDataSource movieNetworkDataSource) {
        return movieNetworkDataSource;
    }

    @Provides
    @Singleton
    MovieDataSource provideMovieRepository(MovieRepository movieRepository) {
        return movieRepository;
    }


}
