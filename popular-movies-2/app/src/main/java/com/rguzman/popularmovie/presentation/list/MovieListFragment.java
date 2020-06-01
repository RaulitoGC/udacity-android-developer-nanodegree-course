package com.rguzman.popularmovie.presentation.list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.presentation.detail.MovieDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class MovieListFragment extends DaggerFragment implements MovieListView, MovieAdapter.ListItemClickListener {

    private static final int GRID_NUM_COLUMNS = 2;
    public static final int UNMARK_AS_FAVORITE_REQUEST = 999;
    public boolean launchDetailFromFavoriteList = false;
    public boolean topRatedForceUpdate = false;

    @BindView(R.id.recyclerView)
    RecyclerView recycler;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MovieListViewModel movieListViewModel;
    private MovieAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);
        this.movieListViewModel.setView(this);
        this.movieListViewModel.init();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.movie_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_most_popular:
                launchDetailFromFavoriteList = false;
                movieListViewModel.loadPopularMovies(true);
                return true;

            case R.id.action_highest_rated:
                launchDetailFromFavoriteList = false;
                movieListViewModel.loadTopRatedMovies(topRatedForceUpdate);
                topRatedForceUpdate = true;
                return true;

            case R.id.action_favorites:
                launchDetailFromFavoriteList = true;
                movieListViewModel.loadFavoritesMovies();
                return true;
        }
        return true;
    }

    @Override
    public void onListItemClick(Movie movie) {
        if (launchDetailFromFavoriteList) {
            startActivityForResult(MovieDetailActivity.getCallingIntent(context(), movie.getMovieId()), UNMARK_AS_FAVORITE_REQUEST);
        } else {
            startActivity(MovieDetailActivity.getCallingIntent(context(), movie.getMovieId()));
        }
    }

    @Override
    public void loadList(List<Movie> movies) {
        adapter.setList(movies);
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyList() {
        adapter.clearList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UNMARK_AS_FAVORITE_REQUEST && resultCode == RESULT_OK) {
            if(launchDetailFromFavoriteList){
                movieListViewModel.loadFavoritesMovies();
            }
        }
    }
}
