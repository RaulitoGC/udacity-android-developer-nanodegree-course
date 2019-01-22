package com.rguzman.popularmovie.presentation.list;

public class MovieListPresenter {

    private MovieListView view;

    public MovieListPresenter() {

    }

    public void setView(MovieListView view) {
        this.view = view;
    }

    public void onDestroy(){
        this.view = null;
    }
}
