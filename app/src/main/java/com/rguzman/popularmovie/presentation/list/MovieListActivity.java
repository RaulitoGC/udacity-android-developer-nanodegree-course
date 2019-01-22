package com.rguzman.popularmovie.presentation.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.presentation.utils.ActivityUtils;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.text_movies_list);

        MovieListPresenter presenter = new MovieListPresenter();
        final MovieListFragment  fragment = MovieListFragment.newInstance(presenter);

        ActivityUtils.addFragment(this, android.R.id.content, fragment);
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

                return true;

            case R.id.action_most_popular:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
