package com.rguzman.techstore.presentation.login;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rguzman.techstore.R;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.domain.model.User;
import com.rguzman.techstore.domain.usecase.Login;
import com.rguzman.techstore.presentation.SingleLiveEvent;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private LiveData<User> userLiveData;
    private SingleLiveEvent<LoginStatus> loginStatus;
    private UserObserver userObserver;

    private final Login login;

    @Inject
    public LoginViewModel(Login login) {
        this.login = login;
    }

    public void init(UserObserver userObserver,SingleLiveEvent<LoginStatus> loginStatus) {
        this.loginStatus = loginStatus;
        this.userObserver = userObserver;
        if (this.userLiveData != null) {
            userLiveData.observeForever(this.userObserver);
        }
    }

    public LiveData<LoginStatus> getStatus(){
        return loginStatus;
    }

    public void login(String email, String password) {
        this.view.showLoading();
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.view.showMessageEmailInvalid();
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 4 || password.length() > 10) {
            this.view.showMessagePasswordError();
            return;
        }

        this.view.setValidInputs();
        this.login.execute(Login.Parameters.loginParameters(email, password), new LoginCallback() {

        });
    }

    private void showError(Exception exception) {
        this.view.hideLoading();
        String message = exception.getMessage();
        if (exception instanceof NetworkConnectionException) {
            message = view.context().getString(R.string.message_exception_network_connection);
        } else if (exception instanceof GenericException) {
            message = view.context().getString(R.string.message_exception_generic);
        }

        view.showError(message);
    }
}
