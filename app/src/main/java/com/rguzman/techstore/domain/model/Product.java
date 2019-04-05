package com.rguzman.techstore.domain.model;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Products", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "categoryId",
        childColumns = "categoryId", onDelete = ForeignKey.CASCADE), indices = {@Index(value = "productId", unique = true)})
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String productId;
    private String image;
    private String price;
    private String description;
    private String categoryId;

    public Product(int id, String productId, String image, String price, String description, String categoryId) {
        this.id = id;
        this.productId = productId;
        this.image = image;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
