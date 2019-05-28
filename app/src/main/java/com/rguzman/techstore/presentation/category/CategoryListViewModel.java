package com.rguzman.techstore.presentation.category;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rguzman.techstore.R;
import com.rguzman.techstore.data.exception.EmptyListException;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.data.preferences.UserPrefs;
import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.usecase.GetCategories;
import com.rguzman.techstore.domain.usecase.Logout;
import com.rguzman.techstore.domain.usecase.UseCaseCallback;
import com.rguzman.techstore.domain.usecase.UseCaseCallbackImpl;
import com.rguzman.techstore.presentation.idlingResource.SimpleIdlingResource;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class CategoryListViewModel extends ViewModel {

    private LiveData<List<Category>> categoryListLiveData;
    private CategoryListObserver categoryListObserver;

    private final GetCategories getCategories;
    private final Logout logout;
    private CategoryListView view;
    @Inject
    UserPrefs userPrefs;

    @Inject
    public CategoryListViewModel(GetCategories getCategories, Logout logout) {
        this.getCategories = getCategories;
        this.logout = logout;
    }

    public void setView(CategoryListView view) {
        this.view = view;
    }

    public void init(@Nullable final SimpleIdlingResource idlingResource) {

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        this.categoryListObserver = new CategoryListObserver(idlingResource);
        if (this.categoryListLiveData != null) {
            categoryListLiveData.observeForever(categoryListObserver);
            return;
        }
        initializeMovies();
    }

    private void initializeMovies() {
        view.showLoading();
        loadCategories(true);
    }

    private void showError(Exception exception) {
        String message = exception.getMessage();

        if (exception instanceof EmptyListException) {
            message = view.context().getString(R.string.message_exception_empty_category_list);
            view.showEmptyList(message);
            return;
        }

        if (exception instanceof NetworkConnectionException) {
            message = view.context().getString(R.string.message_exception_network_connection);
        } else if (exception instanceof GenericException) {
            message = view.context().getString(R.string.message_exception_generic);
        }

        view.showError(message);
    }

    public void loadCategories(boolean forceCache) {
        if (!forceCache) {
            this.view.showRefreshLoading();
        }
        this.getCategories.execute(forceCache, userPrefs.getUser().getToken(), new UseCaseCallback<List<Category>>() {

            @Override
            public void onNetworkResponse(MutableLiveData<List<Category>> liveData) {
                categoryListLiveData = liveData;
                categoryListLiveData.observeForever(categoryListObserver);
            }

            @Override
            public void onDiskResponse(MutableLiveData<List<Category>> liveData) {
                categoryListLiveData = liveData;
                categoryListLiveData.observeForever(categoryListObserver);
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
    }

    public void signOut(){
        this.logout.execute(new UseCaseCallbackImpl<Void>(){
            @Override
            public void onDiskResponse(MutableLiveData<Void> liveData) {
                Timber.d("ON DISK RESPONSE");
                view.logout();
            }
        });
    }

    private final class CategoryListObserver implements Observer<List<Category>> {

        @Nullable
        private final SimpleIdlingResource idlingResource;

        public CategoryListObserver(@Nullable SimpleIdlingResource idlingResource) {
            this.idlingResource = idlingResource;
        }

        @Override
        public void onChanged(List<Category> categories) {
            view.hideLoading();
            view.hideRefreshLoading();
            if (view.isEmptyList()) {
                view.loadListWithAnimation(categories);
            } else {
                view.loadList(categories);
            }
            categoryListLiveData.removeObserver(this);

            if (idlingResource != null) {
                idlingResource.setIdleState(true);
            }
        }
    }
}
