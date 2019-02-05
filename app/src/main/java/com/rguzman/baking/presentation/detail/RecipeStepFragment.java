package com.rguzman.baking.presentation.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Recipe;
import com.rguzman.baking.domain.model.Step;
import com.rguzman.baking.presentation.detail.step.RecipeStepDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public class RecipeStepFragment extends DaggerFragment implements StepAdapter.ListItemClickListener {

    public static final String ARG_RECIPE = "com.rguzmanc.baking.arg.RECIPE";

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredientRecycler;

    @BindView(R.id.step_recycler)
    RecyclerView stepRecycler;

    private Unbinder unbinder;
    private Recipe recipe;
    private StepAdapter stepAdapter;
    private IngredientAdapter ingredientAdapter;

    public static RecipeStepFragment newInstance(Recipe recipe) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);

        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_RECIPE)) {
            this.recipe = getArguments().getParcelable(ARG_RECIPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        this.unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(ingredientRecycler.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(ingredientRecycler.getContext(), R.drawable.recycler_divider));

        this.ingredientAdapter = new IngredientAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientRecycler.setLayoutManager(linearLayoutManager);
        ingredientRecycler.setHasFixedSize(true);
        ingredientRecycler.setAdapter(ingredientAdapter);
        ingredientRecycler.addItemDecoration(dividerItemDecoration);
        ingredientAdapter.setList(recipe.getIngredients());

        this.stepAdapter = new StepAdapter(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        stepRecycler.setLayoutManager(linearLayoutManager1);
        stepRecycler.setHasFixedSize(true);
        stepRecycler.setAdapter(stepAdapter);
        stepAdapter.setList(recipe.getSteps());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onListItemClick(Step step) {
        startActivity(RecipeStepDetailActivity.getCallingIntent(getContext(), step));
    }
}
