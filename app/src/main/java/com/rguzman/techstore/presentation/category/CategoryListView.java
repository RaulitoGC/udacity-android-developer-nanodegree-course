package com.rguzman.techstore.presentation.category;

import android.content.Context;

import com.rguzman.techstore.domain.model.Category;

import java.util.List;

public interface CategoryListView {

    void loadList(List<Category> categories);

    void loadListWithAnimation(List<Category> categories);

    boolean isEmptyList();

    Context context();

    void showError(String message);

    void showEmptyList(String message);

    void showRefreshLoading();

    void hideRefreshLoading();

    void showLoading();

    void hideLoading();
}
