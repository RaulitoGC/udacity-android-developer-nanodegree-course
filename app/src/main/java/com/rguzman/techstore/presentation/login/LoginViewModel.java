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
import com.rguzman.techstore.domain.usecase.UseCaseCallbackImpl;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

  private LiveData<User> userLiveData;
  private UserObserver userObserver;

  private final Login login;
  private LoginView view;

  @Inject
  public LoginViewModel(Login login) {
    this.login = login;
  }

  public void setView(LoginView view) {
    this.view = view;
  }

  public void init() {
    this.userObserver = new UserObserver();
    if (this.userLiveData != null) {
      userLiveData.observeForever(userObserver);
    }
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
    this.login.execute(Login.Parameters.loginParameters(email, password), new UseCaseCallbackImpl<User>() {
      @Override
      public void onNetworkResponse(LiveData<User> liveData) {
        userLiveData = liveData;
        userLiveData.observeForever(userObserver);
      }

      @Override
      public void onError(Exception exception) {
        view.hideLoading();
        showError(exception);
      }
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

  private final class UserObserver implements Observer<User> {

    @Override
    public void onChanged(User user) {
      view.hideLoading();
      view.loginSuccess(user);
      userLiveData.removeObserver(this);
    }
  }
}
