package com.rguzman.popularmovie.presentation.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.GetMovieById;
import com.rguzman.popularmovie.domain.usecase.GetReviewsByMovie;
import com.rguzman.popularmovie.domain.usecase.GetVideosByMovie;
import com.rguzman.popularmovie.domain.usecase.MarkMovieAsFavorite;
import com.rguzman.popularmovie.domain.usecase.UnmarkMovieAsFavorite;
import com.rguzman.popularmovie.domain.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;

public class MovieDetailViewModel extends ViewModel {

    LiveData<Movie> movie;

    private final MarkMovieAsFavorite markMovieAsFavorite;
    private final UnmarkMovieAsFavorite unmarkMovieAsFavorite;
    private final GetReviewsByMovie getPopularMovies;
    private final GetVideosByMovie getVideosByMovie;
    private final GetMovieById getMovieById;
    private MovieDetailView view;

    @Inject
    public MovieDetailViewModel(MarkMovieAsFavorite markMovieAsFavorite, UnmarkMovieAsFavorite unmarkMovieAsFavorite,
                                GetReviewsByMovie getPopularMovies, GetVideosByMovie getVideosByMovie, GetMovieById getMovieById) {
        this.markMovieAsFavorite = markMovieAsFavorite;
        this.unmarkMovieAsFavorite = unmarkMovieAsFavorite;
        this.getPopularMovies = getPopularMovies;
        this.getVideosByMovie = getVideosByMovie;
        this.getMovieById = getMovieById;
    }

    public void setView(MovieDetailView view) {
        this.view = view;
    }

    public void init(int movieId) {
        if (this.movie != null) {
            this.view.addObserver(movie);
            return;
        }
        loadMovieById(movieId);
    }

    private void loadMovieById(int movieId) {
        this.view.removeObserver(movie);
        this.getMovieById.execute(movieId, new GetMovieByIdCallback(){
            @Override
            public void onDiskResponse(LiveData<Movie> liveData) {
                super.onDiskResponse(liveData);

            }
        });
        this.view.addObserver(movie);
    }

    public void markMovieAsFavorite(int movieId) {
        this.markMovieAsFavorite.execute(false, movieId, null);
    }

    public void unmarkMovieAsFavorite(int movieId) {
        this.unmarkMovieAsFavorite.execute(false, movieId, null);
    }

    private final class MovieObserver implements Observer<Movie> {

        @Override
        public void onChanged(@Nullable Movie movie) {

        }
    }

    private class GetMovieByIdCallback implements UseCase.Callback<Movie> {

        @Override
        public void onNetworkResponse(LiveData<Movie> liveData) {

        }

        @Override
        public void onDiskResponse(LiveData<Movie> liveData) {

        }

        @Override
        public void onError(Exception exception) {

        }
    }
}
