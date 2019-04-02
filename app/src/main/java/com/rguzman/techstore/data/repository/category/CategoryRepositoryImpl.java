package com.rguzman.techstore.data.repository.category;

import com.rguzman.techstore.data.repository.category.datasource.CategoryRepository;
import com.rguzman.techstore.data.repository.category.datasource.disk.CategoryDiskDataSource;
import com.rguzman.techstore.data.repository.category.datasource.network.CategoryNetworkDataSource;
import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.usecase.UseCase;

import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryDiskDataSource categoryDiskDataSource;
    private final CategoryNetworkDataSource categoryNetworkDataSource;

    public CategoryRepositoryImpl(CategoryDiskDataSource categoryDiskDataSource, CategoryNetworkDataSource categoryNetworkDataSource) {
        this.categoryDiskDataSource = categoryDiskDataSource;
        this.categoryNetworkDataSource = categoryNetworkDataSource;
    }

    @Override
    public void loadCategories(boolean forceUpdate, UseCase.Callback<List<Category>> callback) {


    }
}
