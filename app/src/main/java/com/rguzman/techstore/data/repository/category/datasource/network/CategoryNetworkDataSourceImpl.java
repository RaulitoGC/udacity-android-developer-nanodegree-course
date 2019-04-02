package com.rguzman.techstore.data.repository.category.datasource.network;

import android.content.Context;

import com.rguzman.techstore.data.net.ApiService;
import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;

public class CategoryNetworkDataSourceImpl implements CategoryNetworkDataSource {

    private final ApiService apiService;
    private final Context context;

    public CategoryNetworkDataSourceImpl(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public void loadCategories(NetworkCallback<List<Category>> callback) {

    }
}
