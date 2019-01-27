package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.rguzman.popularmovie.data.database.AppDatabase;
import com.rguzman.popularmovie.data.exception.GenericException;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.DataWrapper;
import com.rguzman.popularmovie.domain.usecase.GetMoviesUseCase;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class MovieListViewModel extends ViewModel {

    private LiveData<List<Movie>> dataWrapper;

    private final GetMoviesUseCase getMoviesUseCase;
    private final AppDatabase appDatabase;

    @Inject
    public MovieListViewModel(GetMoviesUseCase getMoviesUseCase, AppDatabase appDatabase) {
        this.getMoviesUseCase = getMoviesUseCase;
        this.appDatabase = appDatabase;
    }

    public void init(String path) {
        if (this.dataWrapper != null) {
            return;
        }
        loadMovies(path);
    }

    public void loadMovies(String path) {
        this.dataWrapper = getMoviesUseCase.execute(path);
    }

    public LiveData<List<Movie>> getMovies() {
        return dataWrapper;
    }

    public static class MovieListObserver implements Observer<DataWrapper<List<Movie>>> {

        private final ChangeListener<List<Movie>> listener;

        public MovieListObserver(ChangeListener<List<Movie>> listener) {
            this.listener = listener;
        }

        @Override
        public void onChanged(DataWrapper<List<Movie>> dataWrapper) {
            if (dataWrapper != null) {
                if (dataWrapper.getData() != null) {
                    listener.onSuccess(dataWrapper.getData());
                } else {
                    listener.onException(dataWrapper.getException());
                }
                return;
            }
            listener.onException(new GenericException());
        }
    }

    interface ChangeListener<T> {
        void onSuccess(T dataWrapper);

        void onException(Exception exception);
    }
}
