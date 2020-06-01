package com.rguzman.techstore.data.repository.category.datasource;

import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.usecase.GetCategories;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;

import java.util.List;

public interface CategoryRepository {

    void loadCategories(boolean forceUpdate, String token, UseCaseCallback<List<Category>> callback);

    void cleanCategories(UseCaseCallback<Void> callback);

}
