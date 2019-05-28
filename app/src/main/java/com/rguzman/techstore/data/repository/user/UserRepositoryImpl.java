package com.rguzman.techstore.data.repository.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.data.repository.user.datasource.UserRepository;
import com.rguzman.techstore.data.repository.user.datasource.disk.UserDiskDataSource;
import com.rguzman.techstore.data.repository.user.datasource.network.UserNetworkDataSource;
import com.rguzman.techstore.domain.model.User;
import com.rguzman.techstore.domain.usecase.UseCase;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    private final UserNetworkDataSource userNetworkDataSource;
    private final UserDiskDataSource userDiskDataSource;

    @Inject
    public UserRepositoryImpl(UserNetworkDataSource userNetworkDataSource,
                              UserDiskDataSource userDiskDataSource) {
        this.userNetworkDataSource = userNetworkDataSource;
        this.userDiskDataSource = userDiskDataSource;
    }

    @Override
    public void login(String email, String password, UseCaseCallback<User> callback) {
        this.userNetworkDataSource.login(email, password, new NetworkCallback<User>() {
            @Override
            public void onResponse(LiveData<User> liveData) {
                userDiskDataSource.saveUser(liveData.getValue());
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }

    @Override
    public void logout(UseCaseCallback<Void> callback) {
        this.userDiskDataSource.logout();
        callback.onDiskResponse(null);
    }
}
