package com.rguzman.popularmovie.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.presentation.utils.ActivityUtils;

import dagger.android.support.DaggerAppCompatActivity;

public class MovieDetailActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_MOVIE_ID = "rguzman.popularmovie..extra.MOVIE_ID";

    public static Intent getCallingIntent(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.text_movie_detail);
        if (getIntent() != null && getIntent().hasExtra(EXTRA_MOVIE_ID)) {
            final int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);

            final MovieDetailFragment fragment = MovieDetailFragment.newInstance(movieId);

            ActivityUtils.addFragment(this, android.R.id.content, fragment);
        }
    }
}
