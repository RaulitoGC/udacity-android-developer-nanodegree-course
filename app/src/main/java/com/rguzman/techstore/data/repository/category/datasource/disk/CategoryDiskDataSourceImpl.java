package com.rguzman.techstore.data.repository.category.datasource.disk;

import androidx.lifecycle.LiveData;

import com.rguzman.techstore.data.database.AppDatabase;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;
import java.util.concurrent.Executor;

public class CategoryDiskDataSourceImpl implements CategoryDiskDataSource {

    private final AppDatabase appDatabase;
    private final Executor diskExecutor;

    public CategoryDiskDataSourceImpl(AppDatabase appDatabase, Executor diskExecutor) {
        this.appDatabase = appDatabase;
        this.diskExecutor = diskExecutor;
    }

    @Override
    public void saveCategories(List<Category> categories) {

    }

    @Override
    public LiveData<List<Category>> loadCategories() {
        return null;
    }
}
