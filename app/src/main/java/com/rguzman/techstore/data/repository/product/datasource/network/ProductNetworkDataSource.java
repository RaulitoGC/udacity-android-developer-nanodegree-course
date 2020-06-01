package com.rguzman.techstore.data.repository.product.datasource.network;

import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.data.net.response.ProductResponse;

import java.util.List;

public interface ProductNetworkDataSource {

    void loadProducts(String token,String categoryId, NetworkCallback<List<ProductResponse>> callback);
}
