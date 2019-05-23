package com.rguzman.techstore.presentation.category;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.IdlingResource;

import com.google.android.material.snackbar.Snackbar;
import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.Category;
import com.rguzman.techstore.presentation.idlingResource.SimpleIdlingResource;
import com.rguzman.techstore.presentation.login.LoginActivity;
import com.rguzman.techstore.presentation.product.ProductListActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class CategoryListFragment extends DaggerFragment implements CategoryListView, CategoryAdapter.OnListItemClickListener {

  private static final int GRID_NUM_COLUMNS = 2;

  @BindView(R.id.root_layout)
  View rootLayout;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh_layout)
  SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.progress)
  ProgressBar progress;

  @Inject
  ViewModelProvider.Factory viewModelFactory;
  private CategoryAdapter categoryAdapter;
  private CategoryListViewModel categoryListViewModel;

  @Nullable
  private SimpleIdlingResource mIdlingResource;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.categoryListViewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryListViewModel.class);
    this.categoryListViewModel.setView(this);
    this.categoryListViewModel.init(mIdlingResource);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_card_list, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setHasOptionsMenu(true);

    final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GRID_NUM_COLUMNS);
    this.recyclerView.setLayoutManager(gridLayoutManager);
    this.recyclerView.setHasFixedSize(true);

    this.categoryAdapter = new CategoryAdapter(this);
    this.recyclerView.setAdapter(categoryAdapter);

    this.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context(), R.color.colorAccent));
    this.swipeRefreshLayout.setOnRefreshListener(() -> categoryListViewModel.loadCategories(false));
  }

  @Override
  public void onListItemClick(Category category) {
    startActivity(ProductListActivity.getCallingIntent(context(), category.getCategoryId(), category.getName()));
  }

  @Override
  public void loadList(List<Category> categories) {
    this.categoryAdapter.setList(categories);
    this.swipeRefreshLayout.setEnabled(true);
  }

  @Override
  public void loadListWithAnimation(List<Category> categories) {
    final Context context = recyclerView.getContext();
    final LayoutAnimationController controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

    this.recyclerView.setLayoutAnimation(controller);
    this.categoryAdapter.setList(categories);
    this.recyclerView.scheduleLayoutAnimation();
    this.swipeRefreshLayout.setEnabled(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_log_out, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    Timber.d("item ->" + id);
    Timber.d("log out item ->" + R.id.action_log_out);
    if (id == R.id.action_log_out) {
      categoryListViewModel.signOut();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean isEmptyList() {
    return this.categoryAdapter.getItemCount() == 0;
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
  public void showEmptyList(String message) {
    this.swipeRefreshLayout.setEnabled(false);
    this.categoryAdapter.clearList();
    Snackbar snackbar = Snackbar
            .make(rootLayout, message, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.text_retry), v -> {
              categoryListViewModel.loadCategories(true);
            });
    snackbar.show();
  }

  @Override
  public void showRefreshLoading() {
    this.swipeRefreshLayout.setRefreshing(true);
  }

  @Override
  public void hideRefreshLoading() {
    this.swipeRefreshLayout.setRefreshing(false);
  }

  @Override
  public void showLoading() {
    progress.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    progress.setVisibility(View.GONE);
  }

  @Override
  public void logout() {
    if (getActivity() != null) {
      startActivity(LoginActivity.getCallingIntent(context()));
      getActivity().finish();
    }
  }

  @VisibleForTesting
  @NonNull
  public IdlingResource getIdlingResource() {
    if (mIdlingResource == null) {
      mIdlingResource = new SimpleIdlingResource();
    }
    return mIdlingResource;
  }
}
