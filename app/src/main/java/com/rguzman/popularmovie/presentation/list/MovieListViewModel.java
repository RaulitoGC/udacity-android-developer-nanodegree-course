package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.data.exception.GenericException;
import com.rguzman.popularmovie.data.exception.NetworkConnectionException;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetFavoriteMovies;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;
import com.rguzman.popularmovie.domain.usecase.GetTopRatedMovies;

import java.util.List;

import javax.inject.Inject;


public class MovieListViewModel extends ViewModel {

    private static final String POPULAR = "popular";
    private static final String MOST_RATED = "Highest rated";
    private static final String FAVORITES = "Favorites";


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

    private void showError(Exception exception) {
        String message = exception.getMessage();
        if (exception instanceof NetworkConnectionException) {
            message = view.context().getString(R.string.message_exception_network_connection);
        } else if (exception instanceof GenericException) {
            message = view.context().getString(R.string.message_exception_generic);
        }

        view.showError(message);
    }

    public void init() {
        if (this.movies != null) {
            this.view.addObserver(movies, view.context().getString(R.string.message_exception_empty_list, POPULAR));
            return;
        }
        loadPopularMovies();
    }

    public void loadPopularMovies() {
        this.view.removeObserver(movies);
        this.getPopularMovies.execute(null, new GetPopularMovies.Callback<List<Movie>>() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                movies = liveData;
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });

        this.view.addObserver(movies, view.context().getString(R.string.message_exception_empty_list, POPULAR));
    }

    public void loadTopRatedMovies() {
        this.view.removeObserver(movies);
        this.getTopRatedMovies.execute(null, new GetTopRatedMovies.Callback<List<Movie>>() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                movies = liveData;
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
        this.view.addObserver(movies, view.context().getString(R.string.message_exception_empty_list, MOST_RATED));
    }

    public void loadFavoritesMovies() {
        this.view.removeObserver(movies);
        this.getFavoriteMovies.execute(null, new GetFavoriteMovies.Callback<List<Movie>>() {
            @Override
            public void onResponse(LiveData<List<Movie>> liveData) {
                movies = liveData;
            }

            @Override
            public void onError(Exception exception) {

            }
        });
        this.view.addObserver(movies, view.context().getString(R.string.message_exception_empty_list, FAVORITES));
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
