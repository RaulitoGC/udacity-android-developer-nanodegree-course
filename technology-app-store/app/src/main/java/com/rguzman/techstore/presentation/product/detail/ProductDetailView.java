package com.rguzman.techstore.presentation.product.detail;

import android.content.Context;

import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

public interface ProductDetailView {

    void loadProduct(Product product);

    void loadFeatures(List<Feature> features);

    Context context();

    void showError(String message);
}
