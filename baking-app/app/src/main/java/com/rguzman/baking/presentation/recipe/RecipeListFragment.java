package com.rguzman.baking.presentation.recipe;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Recipe;
import com.rguzman.baking.presentation.IdlingResource.SimpleIdlingResource;
import com.rguzman.baking.presentation.detail.RecipeDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public class RecipeListFragment extends DaggerFragment implements RecipeListView, RecipeAdapter.ListItemClickListener {

    private static final int GRID_NUM_COLUMNS = 3;

    @BindView(R.id.recyclerRecipe)
    RecyclerView recycler;

    private Unbinder unbinder;
    private RecipeListViewModel recipeListViewModel;
    private RecipeAdapter adapter;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIdlingResource();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.recipeListViewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipeListViewModel.class);
        this.recipeListViewModel.setView(this);
        this.recipeListViewModel.init(mIdlingResource);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_recipe, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isTablet = getResources().getBoolean(R.bool.isTable);
        if (isTablet) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context(), GRID_NUM_COLUMNS);
            recycler.setLayoutManager(gridLayoutManager);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context());
            recycler.setLayoutManager(linearLayoutManager);
        }

        recycler.setHasFixedSize(true);

        adapter = new RecipeAdapter(this);
        recycler.setAdapter(adapter);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onListItemClick(Recipe recipe) {
        startActivity(RecipeDetailActivity.getCallingIntent(context(), recipe));
    }

    @Override
    public void loadRecipes(List<Recipe> recipes) {
        adapter.setList(recipes);
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
