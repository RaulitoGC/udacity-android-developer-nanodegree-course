package com.rguzman.techstore.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rguzman.techstore.domain.model.Category;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    abstract CategoryDao categoryDao();
}
