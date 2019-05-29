package com.rguzman.techstore.data.repository.category;

import com.rguzman.techstore.data.database.DataBaseCallback;
import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.data.repository.category.datasource.CategoryRepository;
import com.rguzman.techstore.data.repository.category.datasource.disk.CategoryDiskDataSource;
import com.rguzman.techstore.data.repository.category.datasource.network.CategoryNetworkDataSource;
import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryDiskDataSource categoryDiskDataSource;
    private final CategoryNetworkDataSource categoryNetworkDataSource;

    @Inject
    public CategoryRepositoryImpl(CategoryDiskDataSource categoryDiskDataSource, CategoryNetworkDataSource categoryNetworkDataSource) {
        this.categoryDiskDataSource = categoryDiskDataSource;
        this.categoryNetworkDataSource = categoryNetworkDataSource;
    }

    @Override
    public void loadCategories(boolean forceUpdate, String token, UseCaseCallback<List<Category>> callback) {
        if (forceUpdate) {
            categoryDiskDataSource.loadCategories(new DataBaseCallback<List<Category>>() {
                @Override
                public void onResponse(List<Category> data) {
                    callback.onDiskResponse(data);
                }

                @Override
                public void onError(Exception exception) {
                    callback.onError(exception);
                }
            });

        }

        this.categoryNetworkDataSource.loadCategories(token, new NetworkCallback<List<Category>>() {
            @Override
            public void onResponse(List<Category> data) {
                callback.onNetworkResponse(data);
                categoryDiskDataSource.saveCategories(data);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }

    @Override
    public void cleanCategories(UseCaseCallback<Void> callback) {
        this.categoryDiskDataSource.cleanCategories();
        callback.onDiskResponse(null);
    }
}
