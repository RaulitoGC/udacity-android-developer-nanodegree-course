package com.rguzman.popularmovie.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.rguzman.popularmovie.data.repository.datasource.disk.MovieDao;
import com.rguzman.popularmovie.domain.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
}
