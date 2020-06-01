package com.rguzman.popularmovie.presentation.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.data.exception.GenericException;
import com.rguzman.popularmovie.data.exception.NetworkConnectionException;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.model.Review;
import com.rguzman.popularmovie.domain.model.Video;
import com.rguzman.popularmovie.domain.usecase.GetMovieById;
import com.rguzman.popularmovie.domain.usecase.GetReviewsByMovie;
import com.rguzman.popularmovie.domain.usecase.GetVideosByMovie;
import com.rguzman.popularmovie.domain.usecase.MarkMovieAsFavorite;
import com.rguzman.popularmovie.domain.usecase.UnmarkMovieAsFavorite;

import java.util.List;

import javax.inject.Inject;

public class MovieDetailViewModel extends ViewModel {

    private LiveData<Movie> movieLiveData;
    private MovieObserver movieObserver;

    private LiveData<List<Video>> videosLiveData;
    private VideosListObserver videosListObserver;

    private LiveData<List<Review>> reviewsLiveData;
    private ReviewsListbserver reviewsListbserver;


    private final MarkMovieAsFavorite markMovieAsFavorite;
    private final UnmarkMovieAsFavorite unmarkMovieAsFavorite;
    private final GetReviewsByMovie getReviewsByMovie;
    private final GetVideosByMovie getVideosByMovie;
    private final GetMovieById getMovieById;
    private MovieDetailView view;

    @Inject
    public MovieDetailViewModel(MarkMovieAsFavorite markMovieAsFavorite, UnmarkMovieAsFavorite unmarkMovieAsFavorite,
                                GetReviewsByMovie getReviewsByMovie, GetVideosByMovie getVideosByMovie, GetMovieById getMovieById) {
        this.markMovieAsFavorite = markMovieAsFavorite;
        this.unmarkMovieAsFavorite = unmarkMovieAsFavorite;
        this.getReviewsByMovie = getReviewsByMovie;
        this.getVideosByMovie = getVideosByMovie;
        this.getMovieById = getMovieById;
    }

    public void setView(MovieDetailView view) {
        this.view = view;
    }

    public void init(int movieId) {

        this.movieObserver = new MovieObserver();
        this.videosListObserver = new VideosListObserver();
        this.reviewsListbserver = new ReviewsListbserver();

        if (movieLiveData != null && videosLiveData != null && reviewsLiveData != null) {
            movieLiveData.observeForever(movieObserver);
            videosLiveData.observeForever(videosListObserver);
            reviewsLiveData.observeForever(reviewsListbserver);
            return;
        }
        loadMovieById(movieId);
        loadVideosByMovie(movieId);
        loadReviewsById(movieId);
    }

    private void loadMovieById(int movieId) {
        this.getMovieById.execute(movieId, new GetMovieByIdCallback() {
            @Override
            public void onDiskResponse(LiveData<Movie> liveData) {
                super.onDiskResponse(liveData);
                movieLiveData = liveData;
                movieLiveData.observeForever(movieObserver);
            }
        });
    }

    public void markMovieAsFavorite(int movieId) {
        this.markMovieAsFavorite.execute(movieId);
    }

    public void unmarkMovieAsFavorite(int movieId) {
        this.unmarkMovieAsFavorite.execute(movieId);
    }

    public void loadVideosByMovie(int id) {
        this.getVideosByMovie.execute(id, new GetVideosByMovieCallback() {
            @Override
            public void onNetworkResponse(LiveData<List<Video>> liveData) {
                videosLiveData = liveData;
                videosLiveData.observeForever(videosListObserver);
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
    }

    public void loadReviewsById(int id) {
        this.getReviewsByMovie.execute(id, new GetReviewByVideoCallback() {
            @Override
            public void onNetworkResponse(LiveData<List<Review>> liveData) {
                reviewsLiveData = liveData;
                reviewsLiveData.observeForever(reviewsListbserver);
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
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

    private final class MovieObserver implements Observer<Movie> {

        @Override
        public void onChanged(@Nullable Movie movie) {
            view.showMovie(movie);
            movieLiveData.removeObserver(this);
        }
    }

    private final class VideosListObserver implements Observer<List<Video>> {

        @Override
        public void onChanged(@Nullable List<Video> videos) {
            view.loadVideosByMovie(videos);
            videosLiveData.removeObserver(this);
        }
    }

    private final class ReviewsListbserver implements Observer<List<Review>> {

        @Override
        public void onChanged(@Nullable List<Review> reviews) {
            view.loadReviewByMovie(reviews);
            reviewsLiveData.removeObserver(this);
        }
    }

    private class GetVideosByMovieCallback implements GetVideosByMovie.Callback<List<Video>> {


        @Override
        public void onNetworkResponse(LiveData<List<Video>> liveData) {

        }

        @Override
        public void onDiskResponse(LiveData<List<Video>> liveData) {

        }

        @Override
        public void onError(Exception exception) {

        }
    }

    private class GetReviewByVideoCallback implements GetReviewsByMovie.Callback<List<Review>> {

        @Override
        public void onNetworkResponse(LiveData<List<Review>> liveData) {

        }

        @Override
        public void onDiskResponse(LiveData<List<Review>> liveData) {

        }

        @Override
        public void onError(Exception exception) {

        }
    }

    private class GetMovieByIdCallback implements GetMovieById.Callback<Movie> {

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
