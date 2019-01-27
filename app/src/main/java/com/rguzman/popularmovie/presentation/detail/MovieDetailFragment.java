package com.rguzman.popularmovie.presentation.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.domain.model.Movie;

import dagger.android.support.DaggerFragment;

public class MovieDetailFragment extends DaggerFragment {

    public static final String ARG_MOVIE = "com.rguzmanc.popularmovie.arg.MOVIE";

    public static MovieDetailFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }
}
