package com.rguzman.techstore.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rguzman.techstore.data.database.AppDatabase;
import com.rguzman.techstore.data.net.ApiService;
import com.rguzman.techstore.data.preferences.UserPrefs;
import com.rguzman.techstore.data.preferences.UserPrefsImpl;
import com.rguzman.techstore.data.repository.category.CategoryRepositoryImpl;
import com.rguzman.techstore.data.repository.category.datasource.CategoryRepository;
import com.rguzman.techstore.data.repository.category.datasource.disk.CategoryDiskDataSource;
import com.rguzman.techstore.data.repository.category.datasource.disk.CategoryDiskDataSourceImpl;
import com.rguzman.techstore.data.repository.category.datasource.network.CategoryNetworkDataSource;
import com.rguzman.techstore.data.repository.category.datasource.network.CategoryNetworkDataSourceImpl;
import com.rguzman.techstore.data.repository.product.datasource.disk.ProductDiskDataSource;
import com.rguzman.techstore.data.repository.product.datasource.disk.ProductDiskDataSourceImpl;
import com.rguzman.techstore.data.repository.product.datasource.network.ProductNetworkDataSource;
import com.rguzman.techstore.data.repository.product.datasource.network.ProductNetworkDataSourceImpl;
import com.rguzman.techstore.data.repository.user.UserRepositoryImpl;
import com.rguzman.techstore.data.repository.user.datasource.UserRepository;
import com.rguzman.techstore.data.repository.user.datasource.disk.UserDiskDataSource;
import com.rguzman.techstore.data.repository.user.datasource.disk.UserDiskDataSourceImpl;
import com.rguzman.techstore.data.repository.user.datasource.network.UserNetworkDataSource;
import com.rguzman.techstore.data.repository.user.datasource.network.UserNetworkDataSourceImpl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
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
    return new GsonBuilder().create();
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
    return Room.databaseBuilder(context, AppDatabase.class, "techstore.db")
            .build();
  }

  @Provides
  @Singleton
  SharedPreferences provideSharedPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Provides
  @Singleton
  UserDiskDataSource provideUserDiskDataSource(UserDiskDataSourceImpl userDiskDataSourceImpl) {
    return userDiskDataSourceImpl;
  }


  @Provides
  @Singleton
  UserNetworkDataSource provideUserNetworkDataSource(UserNetworkDataSourceImpl userNetworkDataSourceImpl) {
    return userNetworkDataSourceImpl;
  }

  @Provides
  @Singleton
  UserRepository provideUserRepository(UserRepositoryImpl userRepositoryImpl) {
    return userRepositoryImpl;
  }

  @Provides
  @Singleton
  CategoryRepository provideCategoryRepository(CategoryRepositoryImpl categoryRepositoryImpl) {
    return categoryRepositoryImpl;
  }

  @Provides
  @Singleton
  CategoryNetworkDataSource provideCategoryNetworkDataSource(CategoryNetworkDataSourceImpl categoryNetworkDataSourceImpl) {
    return categoryNetworkDataSourceImpl;
  }

  @Provides
  @Singleton
  CategoryDiskDataSource provideCategoryDiskDataSource(CategoryDiskDataSourceImpl categoryDiskDataSourceImpl) {
    return categoryDiskDataSourceImpl;
  }

  @Provides
  @Singleton
  UserPrefs provideUserPrefs(UserPrefsImpl userPrefs) {
    return userPrefs;
  }
}
