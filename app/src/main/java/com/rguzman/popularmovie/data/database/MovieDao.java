package com.rguzman.popularmovie.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rguzman.popularmovie.domain.model.Movie;

import java.util.List;


@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Movie> movie);

    @Update()
    void update(Movie movie);

    @Query("SELECT * FROM Movies WHERE movieId = :movieId LIMIT 1")
    LiveData<Movie> loadMovieById(int movieId);

    @Query("SELECT * FROM Movies WHERE movieId = :movieId LIMIT 1")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM Movies WHERE isPopular = 1")
    LiveData<List<Movie>> loadPopularMovies();

    @Query("SELECT * FROM Movies WHERE isFavorite = 1")
    LiveData<List<Movie>> loadFavoritesMovies();

    @Query("SELECT * FROM Movies WHERE isTopRated = 1 ORDER BY voteAverage DESC")
    LiveData<List<Movie>> loadTopRatedMovies();
}
