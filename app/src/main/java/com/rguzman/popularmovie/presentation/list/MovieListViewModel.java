package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.data.exception.EmptyListException;
import com.rguzman.popularmovie.data.exception.GenericException;
import com.rguzman.popularmovie.data.exception.NetworkConnectionException;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetFavoriteMovies;
import com.rguzman.popularmovie.domain.usecase.GetPopularMovies;
import com.rguzman.popularmovie.domain.usecase.GetTopRatedMovies;
import com.rguzman.popularmovie.domain.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class MovieListViewModel extends ViewModel {

    private LiveData<List<Movie>> movieListLiveData;
    private MovieListObserver movieListObserver;

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
        } else if (exception instanceof EmptyListException) {
            message = view.context().getString(R.string.message_exception_empty_list, exception.getMessage());
            view.showEmptyList();
        }

        view.showError(message);
    }

    public void init() {
        movieListObserver = new MovieListObserver();
        if (this.movieListLiveData != null) {
            movieListLiveData.observeForever(movieListObserver);
            return;
        }
        initializeMovies();
    }

    private void initializeMovies() {
        loadPopularMovies(false);
    }

    public void loadPopularMovies(boolean forceUpdate) {
        this.getPopularMovies.execute(forceUpdate, new GetPopularMovies.Callback<List<Movie>>() {

            @Override
            public void onNetworkResponse(LiveData<List<Movie>> liveData) {
                movieListLiveData = liveData;
                movieListLiveData.observeForever(movieListObserver);
            }

            @Override
            public void onDiskResponse(LiveData<List<Movie>> liveData) {
                movieListLiveData = liveData;
                movieListLiveData.observeForever(movieListObserver);
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
    }

    public void loadTopRatedMovies(boolean forceUpdate) {
        //this.view.removeObserver(movies);
        this.getTopRatedMovies.execute(forceUpdate, new GetTopRatedMovies.Callback<List<Movie>>() {

            @Override
            public void onNetworkResponse(LiveData<List<Movie>> liveData) {
                movieListLiveData = liveData;
                movieListLiveData.observeForever(movieListObserver);
            }

            @Override
            public void onDiskResponse(LiveData<List<Movie>> liveData) {
                movieListLiveData = liveData;
                movieListLiveData.observeForever(movieListObserver);
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
        //this.view.addObserver(movies);
    }

    public void loadFavoritesMovies() {
        this.getFavoriteMovies.execute(new GetFavoriteListCallback() {
            @Override
            public void onDiskResponse(LiveData<List<Movie>> liveData) {
                super.onDiskResponse(liveData);
                movieListLiveData = liveData;
                movieListLiveData.observeForever(movieListObserver);
                movieListObserver.setRemoveObserver(true);
            }
        });
    }


    private final class MovieListObserver implements Observer<List<Movie>> {

        private boolean removeObserver;

        public MovieListObserver() {
        }

        public void setRemoveObserver(boolean removeObserver) {
            this.removeObserver = removeObserver;
        }

        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            Timber.d(" On change");
            view.loadList(movies);
            movieListLiveData.removeObserver(this);

        }
    }

    private class GetFavoriteListCallback implements UseCase.Callback<List<Movie>> {

        @Override
        public void onNetworkResponse(LiveData<List<Movie>> liveData) {

        }

        @Override
        public void onDiskResponse(LiveData<List<Movie>> liveData) {

        }

        @Override
        public void onError(Exception exception) {

        }
    }
}
