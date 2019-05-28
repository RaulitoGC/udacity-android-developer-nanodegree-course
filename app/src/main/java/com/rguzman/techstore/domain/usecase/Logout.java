package com.rguzman.techstore.domain.usecase;

import androidx.lifecycle.LiveData;

import com.rguzman.techstore.data.repository.category.datasource.CategoryRepository;
import com.rguzman.techstore.data.repository.user.datasource.UserRepository;

import javax.inject.Inject;

public class Logout extends UseCase<Void, Void> {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  @Inject
  public Logout(UserRepository userRepository, CategoryRepository categoryRepository) {
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void execute(UseCaseCallback<Void> callback) {
    this.userRepository.logout(new UseCaseCallbackImpl<Void>() {
      @Override
      public void onDiskResponse(LiveData<Void> liveData) {
        categoryRepository.cleanCategories(callback);
      }
    });
  }
}
