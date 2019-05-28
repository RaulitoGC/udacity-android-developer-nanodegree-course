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

    private MutableLiveData<User> userLiveData;
    private SingleLiveEvent<LoginStatus> loginStatus;

    private final Login login;

    @Inject
    public LoginViewModel(Login login) {
        this.login = login;
    }

    public LiveData<LoginStatus> getStatus() {
        return loginStatus;
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public void login(String email, String password) {
        //this.view.showLoading();
        this.loginStatus.setValue(LoginStatus.SHOW_LOADING);
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //this.view.showMessageEmailInvalid();
            this.loginStatus.setValue(LoginStatus.INVALID_EMAIL);
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 4 || password.length() > 10) {
            //this.view.showMessagePasswordError();
            this.loginStatus.setValue(LoginStatus.INVALID_PASSWORD);
            return;
        }

        //this.view.setValidInputs();
        this.loginStatus.setValue(LoginStatus.VALID_INPUTS);
        this.login.execute(Login.Parameters.loginParameters(email, password), new UseCaseCallbackImpl<User>() {
            @Override
            public void onNetworkResponse(LiveData<User> liveData) {
                userLiveData.setValue(liveData.getValue());
            }

            @Override
            public void onError(Exception exception) {
                //view.hideLoading();
                loginStatus.setValue(LoginStatus.HIDE_LOADING);
                showError(exception);
            }
        });
    }

    private void showError(Exception exception) {
        //this.view.hideLoading();
        if (exception instanceof NetworkConnectionException) {
            this.loginStatus.setValue(LoginStatus.NETWORK_CONNECTION);
//            message = view.context().getString(R.string.message_exception_network_connection);
        } else {
            this.loginStatus.setValue(LoginStatus.GENERIC_ERROR);
//            message = view.context().getString(R.string.message_exception_generic);
        }
    }
}
