package com.rguzman.baking.presentation.detail;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Recipe;
import com.rguzman.baking.domain.model.Step;
import com.rguzman.baking.presentation.detail.step.RecipeStepDetailFragment;
import com.rguzman.baking.presentation.utils.ActivityUtils;
import com.rguzman.baking.presentation.widget.RecipeWidgetProvider;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeDetailActivity extends DaggerAppCompatActivity implements RecipeStepFragment.SelectStepListener {

    private static final String EXTRA_RECIPE = "com.rguzman.baking.extra.RECIPE";

    private Recipe recipe;

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
            recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

            setTitle(recipe.getName());

            ActivityUtils.addFragment(this, R.id.recipe_step_fragment, RecipeStepFragment.newInstance(recipe, this));
        }
    }

    @Override
    public void onSelectStep(Step step) {
        ActivityUtils.replaceFragment(this, R.id.recipe_step_detail_fragment, RecipeStepDetailFragment.newInstance(step));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.widget_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.widget:
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
                RecipeWidgetProvider.updateRecipeWidgets(this, recipe, appWidgetManager, appWidgetIds);
        }
        return true;
    }
}
