package com.rguzman.popularmovie.data.repository.datasource.disk;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public interface DiskDataSource extends MovieDataSource {

    void savePopularMovies(List<Movie> movies);

    void saveTopRatedMovies(List<Movie> movies);

}
