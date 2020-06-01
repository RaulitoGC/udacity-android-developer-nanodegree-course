package com.rguzman.techstore.data.repository.category.datasource.network;

import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;

public interface CategoryNetworkDataSource {

    void loadCategories(String token, NetworkCallback<List<Category>> callback);
}
