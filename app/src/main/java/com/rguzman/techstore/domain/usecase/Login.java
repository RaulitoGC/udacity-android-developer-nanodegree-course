package com.rguzman.techstore.domain.usecase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rguzman.techstore.data.repository.user.datasource.UserRepository;
import com.rguzman.techstore.domain.model.User;

import javax.inject.Inject;

public class Login extends UseCase<Login.Parameters, User> {

  private final UserRepository userRepository;

  @Inject
  public Login(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void execute(boolean forceUpdate, Parameters params, UseCaseCallback<User> callback) {
    this.userRepository.login(params.email, params.password, new UseCaseCallbackImpl<User>() {
      @Override
      public void onNetworkResponse(LiveData<User> liveData) {
        callback.onNetworkResponse(liveData);
      }

      @Override
      public void onError(Exception exception) {
        callback.onError(exception);
      }
    });
  }

  public static final class Parameters {

    private final String email;
    private final String password;

    public Parameters(String email, String password) {
      this.email = email;
      this.password = password;
    }

    public static Parameters loginParameters(String email, String password) {
      return new Parameters(email, password);
    }
  }
}
