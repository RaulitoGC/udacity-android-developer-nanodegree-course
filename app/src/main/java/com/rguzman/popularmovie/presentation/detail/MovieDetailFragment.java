package com.rguzman.popularmovie.presentation.detail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.domain.model.Movie;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

public class MovieDetailFragment extends DaggerFragment implements MovieDetailView {

    public static final String ARG_MOVIE_ID = "com.rguzmanc.popularmovie.arg.MOVIE_ID";

    private int movieId;

    @BindView(R.id.txt_movie_name)
    TextView txtMovieName;
    @BindView(R.id.txt_movie_vote_average)
    TextView txtMovieVoteAverage;
    @BindView(R.id.txt_overview)
    TextView txtOverview;
    @BindView(R.id.txt_movie_release_date)
    TextView txtMovieReleaseDate;
    @BindView(R.id.image_movie_poster)
    ImageView imgMoviePoster;
    @BindView(R.id.btnFavorite)
    ImageButton imgFavorite;


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MovieDetailViewModel movieDetailViewModel;

    public static MovieDetailFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
            this.movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel.class);
            this.movieDetailViewModel.setView(this);
            this.movieDetailViewModel.init(movieId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().setTitle(R.string.text_movie_detail);
        }
    }

    private void populateUi(Movie movie) {
        Glide.with(this)
                .load(movie.getPosterPath())
                .into(imgMoviePoster);

        txtMovieName.setText(movie.getTitle());
        String voteAverage = String.valueOf(movie.getVoteAverage());
        txtMovieVoteAverage.setText(voteAverage);
        txtOverview.setText(movie.getOverview());
        txtMovieReleaseDate.setText(movie.getReleaseDate());
        imgFavorite.setSelected(movie.isFavorite());
    }

    @OnClick(R.id.btnFavorite)
    public void markAsFavorite(View view) {
        view.setSelected(!view.isSelected());
        if (view.isSelected()) {
            this.movieDetailViewModel.markMovieAsFavorite(movieId);
        } else {
            this.movieDetailViewModel.unmarkMovieAsFavorite(movieId);
            if (getActivity() != null) {
                getActivity().setResult(Activity.RESULT_OK);
            }
        }
    }

    @Override
    public void showMovie(Movie movie) {
        populateUi(movie);
    }

    @Override
    public void loadVideosByMovie() {

    }

    @Override
    public void loadReviewByMovie() {

    }
}
