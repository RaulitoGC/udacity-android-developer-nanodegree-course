package com.rguzman.techstore.domain.usecase;

import androidx.lifecycle.LiveData;

import com.rguzman.techstore.data.repository.category.datasource.CategoryRepository;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;

import javax.inject.Inject;

public class GetCategories extends UseCase<String, List<Category>> {

    private final CategoryRepository categoryRepository;

    @Inject
    public GetCategories(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void execute(boolean forceUpdate, String params, Callback<List<Category>> callback) {
        setForceUpdate(forceUpdate);
        this.categoryRepository.loadCategories(isForceUpdate(), params, new Callback<List<Category>>() {
            @Override
            public void onNetworkResponse(LiveData<List<Category>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onDiskResponse(LiveData<List<Category>> liveData) {
                callback.onDiskResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
