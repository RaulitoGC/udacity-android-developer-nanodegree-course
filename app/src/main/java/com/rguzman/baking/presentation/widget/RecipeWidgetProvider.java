package com.rguzman.baking.presentation.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Ingredient;
import com.rguzman.baking.domain.model.Recipe;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, Recipe recipe, AppWidgetManager appWidgetManager,
                                       int[] appWidgetId) {

        ArrayList<Ingredient> list = (ArrayList<Ingredient>) recipe.getIngredients();
        RemoteViews remoteViews = getListIngredientsRemoteView(context, list);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private static RemoteViews getListIngredientsRemoteView(Context context, ArrayList<Ingredient> ingredients) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        Intent intent = ListWidgetService.getIntent(context, ingredients);
        views.setRemoteAdapter(R.id.widget_list, intent);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

