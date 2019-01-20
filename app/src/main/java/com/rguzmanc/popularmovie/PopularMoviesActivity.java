package com.rguzmanc.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rguzmanc.popularmovie.asyntask.FetchPopularMoviesAsynTask;
import com.rguzmanc.popularmovie.exception.NetworkConnectionException;
import com.rguzmanc.popularmovie.model.Movie;
import com.rguzmanc.popularmovie.utils.NetworkUtils;

import java.util.List;

public class PopularMoviesActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener,
        FetchPopularMoviesAsynTask.LoadMoviesCallback {

    private static final int GRID_NUM_COLUMNS = 2;
    public static final String EXTRA_MOVIE = "com.rguzmanc.popularmovie.extra.EXTRA_MOVIE";

    private RecyclerView recyclerView;

    private MovieAdapter adapter;
    private FetchPopularMoviesAsynTask asynTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        this.recyclerView = findViewById(R.id.recyclerView);


        setTitle(R.string.text_pop_movies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, GRID_NUM_COLUMNS);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MovieAdapter(this);
        recyclerView.setAdapter(adapter);

        loadMovies(NetworkUtils.PATH_MOST_POPULAR_MOVIES);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_highest_rated:
                loadMovies(NetworkUtils.PATH_HIGHEST_RATED_MOVIES);
                return true;

            case R.id.action_most_popular:
                loadMovies(NetworkUtils.PATH_MOST_POPULAR_MOVIES);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMovies(String path) {
        asynTask = new FetchPopularMoviesAsynTask(this, this);
        asynTask.execute(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.asynTask.cancel(true);
    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailPopularMoviesActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void onLoadSuccess(List<Movie> movies) {
        this.adapter.setList(movies);
    }

    @Override
    public void onLoadError(Exception exception) {
        String message = exception.getMessage();
        if (exception instanceof NetworkConnectionException) {
            message = getString(R.string.message_exception_network_connection);
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
