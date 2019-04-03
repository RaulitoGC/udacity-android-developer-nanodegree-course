package com.rguzman.techstore.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rguzman.techstore.domain.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Category> categories);

    @Query("SELECT * FROM Categories WHERE categoryId = :categoryId LIMIT 1")
    Category getMovieById(int categoryId);

    @Query("SELECT * FROM Categories")
    LiveData<List<Category>> loadCategories();
}
