package com.rguzman.techstore.presentation.category;

import android.content.Context;

import com.rguzman.techstore.domain.model.Category;

import java.util.List;

public interface CategoryListView {
    void loadList(List<Category> movies);

    Context context();

    void showError(String message);

    void showEmptyList();
}
