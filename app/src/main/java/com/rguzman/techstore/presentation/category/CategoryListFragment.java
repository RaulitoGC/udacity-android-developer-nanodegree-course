package com.rguzman.techstore.presentation.category;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class CategoryListFragment extends DaggerFragment implements CategoryListView, CategoryAdapter.OnListItemClickListener {

    private static final int GRID_NUM_COLUMNS = 2;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CategoryAdapter categoryAdapter;
    private CategoryListViewModel categoryListViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.categoryListViewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryListViewModel.class);
        this.categoryListViewModel.setView(this);
        this.categoryListViewModel.init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GRID_NUM_COLUMNS);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        categoryAdapter = new CategoryAdapter(this);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onListItemClick(Category movie) {

    }

    @Override
    public void loadList(List<Category> list) {
        this.categoryAdapter.setList(list);
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyList() {
        this.categoryAdapter.clearList();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
