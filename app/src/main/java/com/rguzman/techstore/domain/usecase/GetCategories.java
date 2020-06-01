package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.category.datasource.CategoryRepository;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

public class GetCategories extends UseCase<String, List<Category>> {

    private final CategoryRepository categoryRepository;

    @Inject
    public GetCategories(CategoryRepository categoryRepository, @Named("uiExecutor") Executor uiExecutor) {
        super(uiExecutor);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void execute(boolean forceUpdate, String token, UseCaseCallback<List<Category>> callback) {
        this.setForceCache(forceUpdate);
        this.categoryRepository.loadCategories(isForceCache(), token, new UseCaseCallback<List<Category>>() {
            @Override
            public void onNetworkResponse(List<Category> data) {
                callback.onNetworkResponse(data);
            }

            @Override
            public void onDiskResponse(List<Category> data) {
                uiExecutor.execute(() -> callback.onDiskResponse(data));
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
