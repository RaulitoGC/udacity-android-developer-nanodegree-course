package com.rguzman.baking.presentation.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, Recipe recipe, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        RemoteViews remoteViews = getListIngredientsRemoteView(context, recipe);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    public static void updateRecipeWidgets(Context context, Recipe recipe, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, recipe, appWidgetManager, appWidgetId);
        }
    }

    private static RemoteViews getListIngredientsRemoteView(Context context, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        Intent intent = ListWidgetService.getIntent(context, recipe);
        views.setRemoteAdapter(R.id.widget_list, intent);
        views.setTextViewText(R.id.text_title_widget, recipe.getName());
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

