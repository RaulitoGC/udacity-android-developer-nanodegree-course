package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.user.datasource.UserRepository;
import com.rguzman.techstore.domain.model.User;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

public class Login extends UseCase<Login.Parameters, User> {

    private final UserRepository userRepository;

    @Inject
    public Login(UserRepository userRepository, @Named("uiExecutor") Executor uiExecutor) {
        super(uiExecutor);
        this.userRepository = userRepository;
    }

    @Override
    public void execute(boolean forceUpdate, Parameters params, UseCaseCallback<User> callback) {
        this.userRepository.login(params.email, params.password, new UseCaseCallbackImpl<User>() {
            @Override
            public void onNetworkResponse(User liveData) {
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
