package com.rguzman.techstore.data.repository.category.datasource.disk;

import com.rguzman.techstore.data.database.AppDatabase;
import com.rguzman.techstore.data.database.DataBaseCallback;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class CategoryDiskDataSourceImpl implements CategoryDiskDataSource {

    private final AppDatabase appDatabase;
    private final Executor diskExecutor;

    @Inject
    public CategoryDiskDataSourceImpl(AppDatabase appDatabase, @Named("workExecutor") Executor diskExecutor) {
        this.appDatabase = appDatabase;
        this.diskExecutor = diskExecutor;
    }

    @Override
    public void saveCategories(List<Category> categories) {
        if (categories != null) {
            diskExecutor.execute(() -> {
                for (Category category : categories) {
                    Category localMovie = appDatabase.categoryDao().getMovieById(category.getId());
                    if (localMovie != null) {
                        category.setId(localMovie.getId());
                    }
                }
                insertOrUpdate(categories);
            });
        }
    }

    @Override
    public void loadCategories(DataBaseCallback<List<Category>> callback) {
        diskExecutor.execute(() -> {
            List<Category> list = appDatabase.categoryDao().loadCategories();
            if (list != null) {
                callback.onResponse(list);
            } else {
                callback.onError(new GenericException());
            }
        });
    }

    @Override
    public void cleanCategories() {
        diskExecutor.execute(() -> this.appDatabase.categoryDao().deleteCategories());

    }

    private void insertOrUpdate(List<Category> categories) {
        appDatabase.categoryDao().insert(categories);
    }
}
