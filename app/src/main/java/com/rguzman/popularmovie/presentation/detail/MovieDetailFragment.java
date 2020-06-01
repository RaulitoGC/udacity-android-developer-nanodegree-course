package com.rguzman.popularmovie.presentation.detail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.model.Review;
import com.rguzman.popularmovie.domain.model.Video;
import com.rguzman.popularmovie.presentation.utils.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

public class MovieDetailFragment extends DaggerFragment implements MovieDetailView, VideoAdapter.ListItemClickListener {

    public static final String ARG_MOVIE_ID = "com.rguzmanc.popularmovie.arg.MOVIE_ID";

    private int movieId;
    private VideoAdapter videoAdapter;
    private ReviewAdapter reviewAdapter;

    @BindView(R.id.txt_movie_name)
    TextView txtMovieName;
    @BindView(R.id.txt_movie_vote_average)
    TextView txtMovieVoteAverage;
    @BindView(R.id.txt_overview)
    TextView txtOverview;
    @BindView(R.id.txt_movie_release_date)
    TextView txtMovieReleaseDate;
    @BindView(R.id.image_movie_poster)
    ImageView imgMoviePoster;
    @BindView(R.id.btnFavorite)
    ImageButton imgFavorite;
    @BindView(R.id.reviewsRecycler)
    RecyclerView reviewRecycler;
    @BindView(R.id.videosRecycler)
    RecyclerView videosRecycler;


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MovieDetailViewModel movieDetailViewModel;

    public static MovieDetailFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
            this.movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel.class);
            this.movieDetailViewModel.setView(this);
            this.movieDetailViewModel.init(movieId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().setTitle(R.string.text_movie_detail);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        reviewRecycler.setLayoutManager(linearLayoutManager);
        reviewRecycler.setHasFixedSize(true);

        reviewAdapter = new ReviewAdapter();
        reviewRecycler.setAdapter(reviewAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        videosRecycler.setLayoutManager(linearLayoutManager1);
        videosRecycler.setHasFixedSize(true);

        videoAdapter = new VideoAdapter(this);
        videosRecycler.setAdapter(videoAdapter);

    }

    private void populateUi(Movie movie) {
        Glide.with(this)
                .load(movie.getPosterPath())
                .into(imgMoviePoster);

        txtMovieName.setText(movie.getTitle());
        String voteAverage = String.valueOf(movie.getVoteAverage());
        txtMovieVoteAverage.setText(voteAverage);
        txtOverview.setText(movie.getOverview());
        txtMovieReleaseDate.setText(movie.getReleaseDate());
        imgFavorite.setSelected(movie.isFavorite());
    }

    @OnClick(R.id.btnFavorite)
    public void markAsFavorite(View view) {
        view.setSelected(!view.isSelected());
        if (view.isSelected()) {
            this.movieDetailViewModel.markMovieAsFavorite(movieId);
        } else {
            this.movieDetailViewModel.unmarkMovieAsFavorite(movieId);
            if (getActivity() != null) {
                getActivity().setResult(Activity.RESULT_OK);
            }
        }
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
    public void showMovie(Movie movie) {
        populateUi(movie);
    }

    @Override
    public void loadVideosByMovie(List<Video> videos) {

        videoAdapter.setList(videos);
    }

    @Override
    public void loadReviewByMovie(List<Review> reviews) {
        reviewAdapter.setList(reviews);
    }

    @Override
    public void onListItemClick(Video video) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getKey()));
        if (ActivityUtils.isAvailable(getActivity(), intent)) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getKey())));
        }

    }


}
