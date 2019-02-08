package com.rguzman.baking.presentation.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Ingredient;

import java.util.List;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String EXTRA_LIST_INGREDIENTS = "com.rguzman.baking.extra.LIST_INGREDIENTS";

    private Context context;
    private List<Ingredient> ingredients;

    public ListRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        if (intent != null && intent.hasExtra(EXTRA_LIST_INGREDIENTS)) {
            ingredients = intent.getParcelableArrayListExtra(EXTRA_LIST_INGREDIENTS);
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
        this.ingredients.clear();
        this.ingredients = null;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_item_ingredient);
        views.setTextViewText(R.id.txt_ingredient_measure, context.getString(R.string.text_ingredient_measure_label, ingredients.get(position).getMeasure()));
        views.setTextViewText(R.id.txt_ingredient_quantity, context.getString(R.string.text_ingredient_quantity_label, String.valueOf(ingredients.get(position).getQuantity())));
        views.setTextViewText(R.id.txt_ingredient_name, context.getString(R.string.text_ingredient_name_label, ingredients.get(position).getIngredient()));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
