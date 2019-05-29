package com.rguzman.techstore.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rguzman.techstore.domain.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Query("SELECT * FROM Products WHERE productId = :productId LIMIT 1")
    Product getProductById(String productId);

    @Query("SELECT * FROM Products WHERE categoryId = :categoryId")
    List<Product> loadProducts(String categoryId);
}
