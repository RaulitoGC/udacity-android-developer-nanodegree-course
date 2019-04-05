package com.rguzman.techstore.presentation.product;

import android.content.Context;

import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

public interface ProductListView {

  void loadList(List<Product> products);

  void loadListWithAnimation(List<Product> products);

  boolean isEmptyList();

  Context context();

  void showError(String message);

  void showEmptyList(String message);

  void showRefreshLoading();

  void hideRefreshLoading();

  void showLoading();

  void hideLoading();
}
