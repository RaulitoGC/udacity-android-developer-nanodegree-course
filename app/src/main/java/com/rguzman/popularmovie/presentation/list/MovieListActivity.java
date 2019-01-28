package com.rguzman.popularmovie.presentation.list;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.presentation.utils.ActivityUtils;

import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class MovieListActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.text_movies_list);
        if (savedInstanceState == null) {
            Timber.d(" ADD FRAGMENT");
            final MovieListFragment fragment = new MovieListFragment();
            ActivityUtils.addFragment(this, android.R.id.content, fragment);
        }
    }
}
