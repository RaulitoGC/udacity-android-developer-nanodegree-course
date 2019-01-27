package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.data.exception.GenericException;
import com.rguzman.popularmovie.data.exception.NetworkConnectionException;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class MovieListFragment extends DaggerFragment implements MovieAdapter.ListItemClickListener {

    private static final int GRID_NUM_COLUMNS = 2;
    private static final String PATH_MOST_POPULAR_MOVIES = "popular";
    private static final String PATH_HIGHEST_RATED_MOVIES = "top_rated";
    private static final String PATH_FAVORITES = "favorites";

    @BindView(R.id.recyclerView)
    RecyclerView recycler;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MovieListViewModel movieListViewModel;
    private MovieAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);
        this.movieListViewModel.init(PATH_MOST_POPULAR_MOVIES);
        this.movieListViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.setList(movies);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GRID_NUM_COLUMNS);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setHasFixedSize(true);

        adapter = new MovieAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void showError(Exception exception) {
        String message = exception.getMessage();
        if (exception instanceof NetworkConnectionException) {
            message = getString(R.string.message_exception_network_connection);
        } else if (exception instanceof GenericException) {
            message = getString(R.string.exception_message_generic);
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.movie_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_highest_rated:
                movieListViewModel.init(PATH_HIGHEST_RATED_MOVIES);
                return true;

            case R.id.action_most_popular:
                movieListViewModel.init(PATH_MOST_POPULAR_MOVIES);
                return true;

            case R.id.action_favorites:
                movieListViewModel.init(PATH_FAVORITES);
                return true;
        }
        return true;
    }

    @Override
    public void onListItemClick(Movie movie) {

    }
}
