package com.rguzman.popularmovie.data.repository.datasource;


import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.DataWrapper;

import java.util.List;

public interface MovieDataSource {

    LiveData<List<Movie>> getMovies(String path);
}
