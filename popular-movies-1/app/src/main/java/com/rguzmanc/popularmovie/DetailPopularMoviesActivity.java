package com.rguzmanc.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.rguzmanc.popularmovie.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailPopularMoviesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popular_movies_detail);

        setTitle(R.string.text_movie_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(PopularMoviesActivity.EXTRA_MOVIE)) {
            Movie movie = intent.getParcelableExtra(PopularMoviesActivity.EXTRA_MOVIE);

            Picasso.get()
                    .load(movie.getPosterPath())
                    .into((ImageView) findViewById(R.id.image_movie_poster));

            ((TextView) findViewById(R.id.txt_movie_name)).setText(movie.getTitle());
            String voteAverage = String.valueOf(movie.getVoteAverage());
            ((TextView) findViewById(R.id.txt_movie_vote_average)).setText(voteAverage);
            ((TextView) findViewById(R.id.txt_overview)).setText(movie.getOverview());
            ((TextView) findViewById(R.id.txt_movie_release_date)).setText(movie.getReleaseDate());
        }

    }
}
