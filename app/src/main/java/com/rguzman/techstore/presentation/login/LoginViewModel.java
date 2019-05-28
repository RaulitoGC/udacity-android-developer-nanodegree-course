package com.rguzman.techstore.presentation.login;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.domain.model.User;
import com.rguzman.techstore.domain.usecase.Login;
import com.rguzman.techstore.domain.usecase.UseCaseCallbackImpl;
import com.rguzman.techstore.presentation.SingleLiveEvent;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<User> userLiveData;
    private final SingleLiveEvent<LoginStatus> loginStatus;

    private final Login login;

    @Inject
    public LoginViewModel(Login login, MutableLiveData<User> userLiveData, SingleLiveEvent<LoginStatus> loginStatus) {
        this.login = login;
        this.userLiveData = userLiveData;
        this.loginStatus = loginStatus;
    }

    public LiveData<LoginStatus> getStatus() {
        return loginStatus;
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public void login(String email, String password) {
        this.loginStatus.setValue(LoginStatus.SHOW_LOADING);
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.loginStatus.setValue(LoginStatus.INVALID_EMAIL);
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 4 || password.length() > 10) {
            this.loginStatus.setValue(LoginStatus.INVALID_PASSWORD);
            return;
        }

        this.loginStatus.setValue(LoginStatus.VALID_INPUTS);
        this.login.execute(Login.Parameters.loginParameters(email, password), new UseCaseCallbackImpl<User>() {
            @Override
            public void onNetworkResponse(LiveData<User> liveData) {
                userLiveData.setValue(liveData.getValue());
            }

            @Override
            public void onError(Exception exception) {
                loginStatus.setValue(LoginStatus.HIDE_LOADING);
                showError(exception);
            }
        });
    }

    private void showError(Exception exception) {
        if (exception instanceof NetworkConnectionException) {
            this.loginStatus.setValue(LoginStatus.NETWORK_CONNECTION);
        } else {
            this.loginStatus.setValue(LoginStatus.GENERIC_ERROR);
        }
    }
}
