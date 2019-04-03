package com.rguzman.techstore.domain.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categories", indices = {@Index(value = "categoryId", unique = true)})
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
