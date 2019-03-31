package com.rguzman.techstore.data.repository.user.datasource.network;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.data.net.ApiService;
import com.rguzman.techstore.data.net.NetworkCallback;
import com.rguzman.techstore.data.net.request.LoginBody;
import com.rguzman.techstore.domain.model.User;
import com.rguzman.techstore.presentation.utils.NetworkUtils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserNetworkDataSourceImpl implements UserNetworkDataSource {

    private final ApiService apiService;
    private final Context context;

    @Inject
    public UserNetworkDataSourceImpl(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public void login(String email, String password, NetworkCallback<User> callback) {
        if (!NetworkUtils.isThereNetworkConnection(context)) {
            callback.onError(new NetworkConnectionException());
        } else {
            LoginBody loginBody = new LoginBody(email, password);
            Call<User> call = this.apiService.login(loginBody);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        final MutableLiveData<User> liveData = new MutableLiveData<>();
                        liveData.setValue(response.body());
                        callback.onResponse(liveData);
                    } else {
                        callback.onError(new GenericException());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                    callback.onError(new GenericException());
                }
            });
        }
    }
}
