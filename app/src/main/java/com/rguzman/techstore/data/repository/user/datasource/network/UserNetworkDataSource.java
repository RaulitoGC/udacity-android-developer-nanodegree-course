package com.rguzman.techstore.data.repository.user.datasource.network;

import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.domain.model.User;

public interface UserNetworkDataSource {

    void login(String email, String password, NetworkCallback<User> callback);
}
