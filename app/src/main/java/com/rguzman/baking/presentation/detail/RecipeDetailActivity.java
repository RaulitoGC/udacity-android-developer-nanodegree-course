package com.rguzman.baking.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Recipe;
import com.rguzman.baking.presentation.utils.ActivityUtils;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeDetailActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_RECIPE = "com.rguzman.baking.extra.RECIPE";

    public static Intent getCallingIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (getIntent() != null && getIntent().hasExtra(EXTRA_RECIPE)) {
            Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

            setTitle(recipe.getName());

            ActivityUtils.addFragment(this, R.id.recipe_step_fragment, RecipeStepFragment.newInstance(recipe));
        }
    }
}
