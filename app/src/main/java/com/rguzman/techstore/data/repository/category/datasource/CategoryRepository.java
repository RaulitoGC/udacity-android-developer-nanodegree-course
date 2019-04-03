package com.rguzman.techstore.data.repository.category.datasource;

import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.usecase.GetCategories;

import java.util.List;

public interface CategoryRepository {

    void loadCategories(boolean forceUpdate, String token, GetCategories.Callback<List<Category>> callback);

    void loadProducts(boolean forceUpdate, String token, String categoryId, GetCategories.Callback<List<Category>> callback);

}
