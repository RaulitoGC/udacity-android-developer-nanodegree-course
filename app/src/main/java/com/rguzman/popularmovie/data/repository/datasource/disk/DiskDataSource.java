package com.rguzman.popularmovie.data.repository.datasource.disk;

import com.rguzman.popularmovie.data.repository.datasource.MovieDataSource;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;

public interface DiskDataSource extends MovieDataSource {

    void saveMovies(List<Movie> movies);
}
