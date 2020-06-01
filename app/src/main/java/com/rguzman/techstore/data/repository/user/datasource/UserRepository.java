package com.rguzman.techstore.data.repository.user.datasource;

import com.rguzman.techstore.domain.model.User;
import com.rguzman.techstore.domain.usecase.Login;
import com.rguzman.techstore.domain.usecase.Logout;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;

public interface UserRepository {

    void login(String email, String password, UseCaseCallback<User> callback);

    void logout(UseCaseCallback<Void> callback);
}
