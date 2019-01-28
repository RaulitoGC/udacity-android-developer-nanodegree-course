package com.rguzman.popularmovie.data.repository.datasource.disk;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.database.AppDatabase;
import com.rguzman.popularmovie.data.database.MovieDao;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MovieDiskDataSource implements DiskDataSource {

    private final AppDatabase appDatabase;
    private final Executor diskExecutor;

    @Inject
    public MovieDiskDataSource(AppDatabase appDatabase, Executor diskExecutor) {
        this.appDatabase = appDatabase;
        this.diskExecutor = diskExecutor;
    }

    @Override
    public LiveData<List<Movie>> loadPopularMovies() {
        return appDatabase.movieDao().loadPopularMovies();
    }

    @Override
    public LiveData<List<Movie>> loadTopRatedMovies() {
        return appDatabase.movieDao().loadTopRatedMovies();
    }

    @Override
    public LiveData<List<Movie>> loadFavoritesMovies() {
        return appDatabase.movieDao().loadFavoritesMovies();
    }

    @Override
    public void savePopularMovies(List<Movie> movies) {
        if (movies != null) {
            diskExecutor.execute(() -> {
                for (Movie movie : movies) {
                    Movie localMovie = appDatabase.movieDao().getMovieById(movie.getMovieId());
                    movie.setPopular(true);
                    if (localMovie != null) {
                        movie.setId(localMovie.getId());
                        movie.setTopRated(localMovie.isTopRated());
                        movie.setFavorite(localMovie.isFavorite());
                    }
                }
                insertOrUpdate(movies);
            });
        }
    }

    @Override
    public void saveTopRatedMovies(List<Movie> movies) {
        if (movies != null) {
            diskExecutor.execute(() -> {
                for (Movie movie : movies) {
                    Movie localMovie = appDatabase.movieDao().getMovieById(movie.getMovieId());
                    movie.setTopRated(true);
                    if (localMovie != null) {
                        movie.setId(localMovie.getId());
                        movie.setPopular(localMovie.isPopular());
                        movie.setFavorite(localMovie.isFavorite());
                    }
                }
                insertOrUpdate(movies);
            });
        }
    }

    @Override
    public void saveFavoriteMovie(Movie movie) {
        if (movie != null) {
            diskExecutor.execute(() -> {
                MovieDao movieDao = appDatabase.movieDao();
                movie.setFavorite(true);
                movieDao.update(movie);
            });
        }
    }

    @Override
    public void unSaveFavoriteMovie(Movie movie) {
        if (movie != null) {
            diskExecutor.execute(() -> {
                MovieDao movieDao = appDatabase.movieDao();
                movie.setFavorite(false);
                movieDao.update(movie);
            });
        }
    }

    private void insertOrUpdate(List<Movie> movies) {
        appDatabase.movieDao().insert(movies);
    }
}
