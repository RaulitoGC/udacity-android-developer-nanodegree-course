package com.rguzman.popularmovie.data.repository;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.BuildConfig;
import com.rguzman.popularmovie.data.database.AppDatabase;
import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.data.repository.datasource.network.ApiService;
import com.rguzman.popularmovie.data.repository.datasource.network.response.MovieListResponse;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.DataWrapper;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Singleton
public class MovieRepository implements MovieDataSource {

    private final Executor executor;
    private final ApiService apiService;
    private final AppDatabase appDatabase;


    @Inject
    public MovieRepository(Executor executor, ApiService apiService, AppDatabase appDatabase) {
        this.executor = executor;
        this.apiService = apiService;
        this.appDatabase = appDatabase;
    }


    @Override
    public LiveData<List<Movie>> getMovies(String path) {
        refreshFromNetwork(path);
        return appDatabase.movieDao().loadAllMovies();
    }

    private void refreshFromNetwork(String path) {
        apiService.getMovies(path, BuildConfig.MOVIE_API_KEY).enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                executor.execute(() -> {
                    List<Movie> movies = response.body().getResults();
                    for (Movie movie : movies) {
                        appDatabase.movieDao().insert(movie);
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {

            }
        });

    }
}
