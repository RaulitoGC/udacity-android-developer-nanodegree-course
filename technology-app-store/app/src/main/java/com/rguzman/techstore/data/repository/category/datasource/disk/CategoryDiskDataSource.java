package com.rguzman.techstore.data.repository.category.datasource.disk;

import com.rguzman.techstore.data.database.DataBaseCallback;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;

public interface CategoryDiskDataSource {

    void saveCategories(List<Category> categories);

    void loadCategories(DataBaseCallback<List<Category>> callback);

    void cleanCategories();
}
