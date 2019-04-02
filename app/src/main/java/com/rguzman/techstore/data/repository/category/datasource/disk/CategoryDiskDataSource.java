package com.rguzman.techstore.data.repository.category.datasource.disk;

import androidx.lifecycle.LiveData;

import com.rguzman.techstore.domain.model.Category;

import java.util.List;

public interface CategoryDiskDataSource {

    void saveCategories(List<Category> categories);

    LiveData<List<Category>> loadCategories();
}
