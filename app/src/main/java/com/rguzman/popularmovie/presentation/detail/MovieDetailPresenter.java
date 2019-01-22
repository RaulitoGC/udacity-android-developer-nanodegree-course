package com.rguzman.popularmovie.presentation.detail;

public class MovieDetailPresenter {

    private MovieDetailView view;

    public MovieDetailPresenter() {
    }

    public void setView(MovieDetailView view) {
        this.view = view;
    }

    public void onDestroy(){
        this.view = null;
    }
}
