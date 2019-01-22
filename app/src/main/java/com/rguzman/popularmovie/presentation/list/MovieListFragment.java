package com.rguzman.popularmovie.presentation.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rguzman.popularmovie.R;

public class MovieListFragment extends Fragment implements MovieListView {

    private MovieListPresenter presenter;

    public static MovieListFragment newInstance(MovieListPresenter presenter) {
        MovieListFragment fragment = new MovieListFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    public void setPresenter(MovieListPresenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.presenter.onDestroy();
    }
}
