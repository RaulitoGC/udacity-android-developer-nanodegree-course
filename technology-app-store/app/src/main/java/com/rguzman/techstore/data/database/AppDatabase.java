package com.rguzman.techstore.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

@Database(entities = {Category.class, Product.class, Feature.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract FeatureDao featureDao();
}
