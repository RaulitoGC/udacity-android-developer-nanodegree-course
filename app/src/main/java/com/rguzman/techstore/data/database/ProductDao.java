package com.rguzman.techstore.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Product> products);

    @Query("SELECT * FROM Products WHERE productId = :productId LIMIT 1")
    Product getProductById(String productId);

    @Query("SELECT * FROM Products WHERE categoryId = :categoryId")
    LiveData<List<Product>> loadProducts(String categoryId);
}
