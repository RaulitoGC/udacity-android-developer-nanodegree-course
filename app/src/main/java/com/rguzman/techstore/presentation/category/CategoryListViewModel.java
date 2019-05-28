package com.rguzman.techstore.presentation.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rguzman.techstore.data.exception.EmptyListException;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.data.preferences.UserPrefs;
import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.usecase.GetCategories;
import com.rguzman.techstore.domain.usecase.Logout;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;
import com.rguzman.techstore.domain.usecase.UseCaseCallbackImpl;
import com.rguzman.techstore.presentation.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class CategoryListViewModel extends ViewModel {

  private MutableLiveData<List<Category>> categoryListLiveData;
  private SingleLiveEvent<CategoryListStatus> categoryListStatus;

  private final GetCategories getCategories;
  private final Logout logout;

  @Inject
  UserPrefs userPrefs;

  @Inject
  public CategoryListViewModel(GetCategories getCategories, Logout logout, MutableLiveData<List<Category>> categoryListLiveData, SingleLiveEvent<CategoryListStatus> categoryListStatus) {
    this.getCategories = getCategories;
    this.logout = logout;
    this.categoryListLiveData = categoryListLiveData;
    this.categoryListStatus = categoryListStatus;
  }

  public MutableLiveData<List<Category>> getCategoryList() {
    return categoryListLiveData;
  }

  public SingleLiveEvent<CategoryListStatus> getCategoryListStatus() {
    return categoryListStatus;
  }

  public void initializeMovies() {
    this.categoryListStatus.setValue(CategoryListStatus.SHOW_LOADING);
    loadCategories(true);
  }

  private void showError(Exception exception) {
    if (exception instanceof EmptyListException) {
      this.categoryListStatus.setValue(CategoryListStatus.EMPTY_LIST);
      return;
    }

    if (exception instanceof NetworkConnectionException) {
      this.categoryListStatus.setValue(CategoryListStatus.NETWORK_CONNECTION_ERROR);
    } else if (exception instanceof GenericException) {
      this.categoryListStatus.setValue(CategoryListStatus.GENERIC_ERROR);
    }
  }

  public void loadCategories(boolean forceCache) {
    if (!forceCache) {
      this.categoryListStatus.setValue(CategoryListStatus.SHOW_REFRESH_LOADING);
    }
    this.getCategories.execute(forceCache, userPrefs.getUser().getToken(), new UseCaseCallback<List<Category>>() {

      @Override
      public void onNetworkResponse(LiveData<List<Category>> liveData) {
        categoryListStatus.setValue(CategoryListStatus.HIDE_LOADING);
        categoryListStatus.setValue(CategoryListStatus.HIDE_REFRESH_LOADING);
        categoryListLiveData.setValue(liveData.getValue());
      }

      @Override
      public void onDiskResponse(LiveData<List<Category>> liveData) {
        if(liveData.getValue() != null){
          categoryListLiveData.setValue(liveData.getValue());
        }
      }

      @Override
      public void onError(Exception exception) {
        showError(exception);
      }
    });
  }

  public void signOut() {
    this.logout.execute(new UseCaseCallbackImpl<Void>() {
      @Override
      public void onDiskResponse(LiveData<Void> liveData) {
        categoryListStatus.setValue(CategoryListStatus.LOG_OUT);
      }
    });
  }
}
