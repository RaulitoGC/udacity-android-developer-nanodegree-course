package com.rguzman.baking.data.repository.datasource.network;

import com.rguzman.baking.data.net.NetworkCallback;
import com.rguzman.baking.domain.model.Recipe;

import java.util.List;

public interface NetworkDataSource {

    void loadPopularMovies(NetworkCallback<List<Recipe>> callback);
}
