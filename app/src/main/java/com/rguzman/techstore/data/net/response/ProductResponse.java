package com.rguzman.techstore.data.net.response;

import java.util.List;

public class ProductResponse {
    private int id;
    private String productId;
    private String image;
    private String price;
    private String description;
    private List<FeatureResponse> features;

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

    public List<FeatureResponse> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureResponse> features) {
        this.features = features;
    }
}
