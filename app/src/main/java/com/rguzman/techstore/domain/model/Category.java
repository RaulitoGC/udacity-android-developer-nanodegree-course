package com.rguzman.techstore.domain.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category", indices = {@Index(value = "categoryId", unique = true)})
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String categoryId;
    private String name;
    private String image;

    public Category() {
    }

    public Category(int id, String categoryId, String name, String image) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.image = image;
    }
}
