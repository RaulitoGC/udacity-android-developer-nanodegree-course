package com.rguzman.techstore.data.repository.category.datasource;

import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.usecase.GetCategories;

import java.util.List;

public interface CategoryRepository {

    void loadCategories(boolean forceUpdate, GetCategories.Callback<List<Category>> callback);

}
