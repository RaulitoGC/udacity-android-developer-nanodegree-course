package com.rguzman.techstore.domain.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Features", foreignKeys = @ForeignKey(entity = Product.class, parentColumns = "productId",
        childColumns = "productId", onDelete = ForeignKey.CASCADE))
public class Feature {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String label;
    private String productId;

    public Feature(int id, String name, String label, String productId) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}