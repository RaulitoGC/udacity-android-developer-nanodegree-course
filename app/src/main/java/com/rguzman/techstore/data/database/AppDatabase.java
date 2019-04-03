package com.rguzman.techstore.data.database;

import com.rguzman.techstore.domain.model.Category;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

   public abstract CategoryDao categoryDao();
}
