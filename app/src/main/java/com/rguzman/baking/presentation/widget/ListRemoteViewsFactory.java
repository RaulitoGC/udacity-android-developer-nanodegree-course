package com.rguzman.baking.presentation.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Recipe;

import timber.log.Timber;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String EXTRA_RECIPE = "com.rguzman.baking.extra.RECIPE";

    private Context context;
    private Recipe recipe;

    public ListRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        if (intent != null && intent.hasExtra(EXTRA_RECIPE)) {
            String recipeJson = intent.getStringExtra(EXTRA_RECIPE);
            recipe = new Gson().fromJson(recipeJson, Recipe.class);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        this.recipe.getIngredients().clear();
        this.recipe = null;
    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_item_ingredient);
        views.setTextViewText(R.id.txt_ingredient_measure, context.getString(R.string.text_ingredient_measure_label, recipe.getIngredients().get(position).getMeasure()));
        views.setTextViewText(R.id.txt_ingredient_quantity, context.getString(R.string.text_ingredient_quantity_label, String.valueOf(recipe.getIngredients().get(position).getQuantity())));
        views.setTextViewText(R.id.txt_ingredient_name, context.getString(R.string.text_ingredient_name_label, recipe.getIngredients().get(position).getIngredient()));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
