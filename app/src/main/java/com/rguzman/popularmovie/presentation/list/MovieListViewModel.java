package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetFavoriteMovies;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;
import com.rguzman.popularmovie.domain.usecase.GetTopRatedMovies;

import java.util.List;

import javax.inject.Inject;


public class MovieListViewModel extends ViewModel {

    private LiveData<List<Movie>> movies;

    private final GetPopularMovies getPopularMovies;
    private final GetTopRatedMovies getTopRatedMovies;
    private final GetFavoriteMovies getFavoriteMovies;
    private MovieListView view;

    @Inject
    public MovieListViewModel(GetPopularMovies getPopularMovies, GetTopRatedMovies getTopRatedMovies,
                              GetFavoriteMovies getFavoriteMovies) {
        this.getPopularMovies = getPopularMovies;
        this.getTopRatedMovies = getTopRatedMovies;
        this.getFavoriteMovies = getFavoriteMovies;
    }

    public void setView(MovieListView view) {
        this.view = view;
    }

    public void init() {
        if (this.movies != null) {
            this.view.addObserver(movies);
            return;
        }
        loadPopularMovies();
    }

    public void loadPopularMovies() {
        this.view.removeObserver(movies);
        this.movies = getPopularMovies.execute(null);
        this.view.addObserver(movies);
    }

    public void loadTopRatedMovies() {
        this.view.removeObserver(movies);
        this.movies = getTopRatedMovies.execute(null);
        this.view.addObserver(movies);
    }

    public void loadFavoritesMovies() {
        this.view.removeObserver(movies);
        this.movies = getFavoriteMovies.execute(null);
        this.view.addObserver(movies);
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
