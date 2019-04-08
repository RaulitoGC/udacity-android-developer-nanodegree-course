package com.rguzman.techstore.presentation.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.presentation.product.detail.ProductDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class ProductListFragment extends DaggerFragment implements ProductListView, ProductAdapter.OnListItemClickListener {

    private static final String ARGS_CATEGORY_ID = "com.rguzman.techstore.args.CATEGORY_ID";
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
    private ProductAdapter productAdapter;
    private ProductListViewModel productListViewModel;
    private String categoryId;

    public static ProductListFragment newInstance(String categoryId) {
        Bundle args = new Bundle();
        args.putString(ARGS_CATEGORY_ID, categoryId);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.productListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductListViewModel.class);
        this.productListViewModel.setView(this);
        this.productListViewModel.init(categoryId);
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

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GRID_NUM_COLUMNS);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setHasFixedSize(true);

        this.productAdapter = new ProductAdapter(this);
        this.recyclerView.setAdapter(productAdapter);

        this.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context(), R.color.colorAccent));
        if (getArguments() != null && getArguments().containsKey(ARGS_CATEGORY_ID)) {
            this.categoryId = getArguments().getString(ARGS_CATEGORY_ID);
        }
        this.swipeRefreshLayout.setOnRefreshListener(() -> productListViewModel.loadProducts(false, categoryId));
    }

    @Override
    public void loadList(List<Product> products) {
        this.productAdapter.setList(products);
        this.swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadListWithAnimation(List<Product> products) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        this.recyclerView.setLayoutAnimation(controller);
        this.productAdapter.setList(products);
        this.recyclerView.scheduleLayoutAnimation();
        this.swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public boolean isEmptyList() {
        return this.productAdapter.getItemCount() == 0;
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
        this.productAdapter.clearList();
        Snackbar snackbar = Snackbar
                .make(rootLayout, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.text_retry), v -> {
                    productListViewModel.loadProducts(true, categoryId);
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
        this.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.progress.setVisibility(View.GONE);
    }

    @Override
    public void onListItemClick(Product product, AppCompatImageView productImage) {

        Intent intent = ProductDetailActivity.getCallingIntent(context(), product.getProductId());

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), productImage, getString(R.string.text_product_image_shared_element));
        startActivity(intent, options.toBundle());
    }
}
