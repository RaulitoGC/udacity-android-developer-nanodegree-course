package com.rguzman.techstore.domain.usecase;

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

    }
}
