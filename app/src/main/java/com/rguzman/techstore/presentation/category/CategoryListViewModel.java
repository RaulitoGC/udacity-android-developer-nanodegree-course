package com.rguzman.techstore.presentation.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rguzman.techstore.R;
import com.rguzman.techstore.data.exception.EmptyListException;
import com.rguzman.techstore.data.exception.GenericException;
import com.rguzman.techstore.data.exception.NetworkConnectionException;
import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.domain.usecase.GetCategories;

import java.util.List;

import javax.inject.Inject;

public class CategoryListViewModel extends ViewModel {

    private LiveData<List<Category>> categoryListLiveData;
    private CategoryListObserver categoryListObserver;

    private final GetCategories getCategories;
    private CategoryListView view;

    @Inject
    public CategoryListViewModel(GetCategories getCategories) {
        this.getCategories = getCategories;
    }

    public void setView(CategoryListView view) {
        this.view = view;
    }

    public void init() {
        this.categoryListObserver = new CategoryListObserver();
        if (this.categoryListLiveData != null) {
            categoryListLiveData.observeForever(categoryListObserver);
            return;
        }
        initializeMovies();
    }

    private void initializeMovies() {
        loadPopularMovies(false);
    }

    private void showError(Exception exception) {
        String message = exception.getMessage();
        if (exception instanceof NetworkConnectionException) {
            message = view.context().getString(R.string.message_exception_network_connection);
        } else if (exception instanceof GenericException) {
            message = view.context().getString(R.string.message_exception_generic);
        } else if (exception instanceof EmptyListException) {
            message = view.context().getString(R.string.message_exception_empty_list);
            view.showEmptyList();
        }

        view.showError(message);
    }

    public void loadPopularMovies(boolean forceUpdate) {
        this.getCategories.execute(forceUpdate, new GetCategories.Callback<List<Category>>() {

            @Override
            public void onNetworkResponse(LiveData<List<Category>> liveData) {
                categoryListObserver.setRemoveObserver(true);
                categoryListLiveData = liveData;
                categoryListLiveData.observeForever(categoryListObserver);
            }

            @Override
            public void onDiskResponse(LiveData<List<Category>> liveData) {
                categoryListObserver.setRemoveObserver(true);
                categoryListLiveData = liveData;
                categoryListLiveData.observeForever(categoryListObserver);
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
    }

    private final class CategoryListObserver implements Observer<List<Category>> {

        private boolean removeObserver;

        public CategoryListObserver() {
            this.removeObserver = true;
        }

        public void setRemoveObserver(boolean removeObserver) {
            this.removeObserver = removeObserver;
        }

        @Override
        public void onChanged(List<Category> categories) {
            view.hideLoading();
            view.loadList(categories);
            categoryListLiveData.removeObserver(this);
        }
    }


}
