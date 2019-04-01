package com.rguzman.techstore.presentation.login;

import android.content.Context;

import com.rguzman.techstore.domain.model.User;

public interface LoginView {

    Context context();

    void showError(String message);

    void loginSuccess(User user);

    void showMessageEmailInvalid();

    void showMessagePasswordError();

    void setValidInputs();

    void showLoading();

    void hideLoading();

}
