package com.rguzman.techstore.domain.usecase;

import com.rguzman.techstore.data.repository.category.datasource.CategoryRepository;
import com.rguzman.techstore.data.repository.user.datasource.UserRepository;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

public class Logout extends UseCase<Void, Void> {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Inject
    public Logout(UserRepository userRepository, CategoryRepository categoryRepository, @Named("uiExecutor") Executor uiExecutor) {
        super(uiExecutor);
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void execute(UseCaseCallback<Void> callback) {
        this.userRepository.logout(new UseCaseCallbackImpl<Void>() {
            @Override
            public void onDiskResponse(Void data) {
                categoryRepository.cleanCategories(callback);
            }
        });
    }
}
