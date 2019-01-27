package com.rguzman.popularmovie.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.presentation.utils.ActivityUtils;

import dagger.android.support.DaggerAppCompatActivity;

public class MovieDetailActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_MOVIE = "meltwater.signin.password.extra.MOVIE";

    public static Intent getCallingIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.text_movie_detail);
        if (getIntent() != null && getIntent().hasExtra(EXTRA_MOVIE)) {
            final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            final MovieDetailFragment fragment = MovieDetailFragment.newInstance(movie);

            ActivityUtils.addFragment(this, android.R.id.content, fragment);
        }
    }
}
