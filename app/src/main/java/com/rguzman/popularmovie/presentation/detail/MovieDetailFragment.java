package com.rguzman.popularmovie.presentation.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.domain.Movie;

public class MovieDetailFragment extends Fragment {

    public static final String ARG_MOVIE = "com.rguzmanc.popularmovie.arg.MOVIE";

    private MovieDetailPresenter presenter;

    public static MovieDetailFragment newInstance(Movie movie, MovieDetailPresenter presenter) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setPresenter(presenter);
        fragment.setArguments(args);
        return fragment;
    }

    public void setPresenter(MovieDetailPresenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.presenter.onDestroy();
    }
}
