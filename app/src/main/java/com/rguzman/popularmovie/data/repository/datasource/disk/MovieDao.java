package com.rguzman.popularmovie.data.repository.datasource.disk;

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
    void insert(Movie movie);

    @Update
    void update(List<Movie> document);

    @Query("SELECT * FROM Movies WHERE id = :id LIMIT 1")
    LiveData<Movie> getById(int id);

    @Query("SELECT * FROM Movies")
    LiveData<List<Movie>> loadAllMovies();

    @Query("SELECT * FROM Movies WHERE isFavorite = 1")
    LiveData<List<Movie>> listAllFavorites();
}
