package com.rguzman.techstore.data.repository.user.datasource;

import com.rguzman.techstore.domain.model.User;
import com.rguzman.techstore.domain.usecase.Login;

public interface UserRepository {

    void login(String email, String password, Login.Callback<User> callback);
}
