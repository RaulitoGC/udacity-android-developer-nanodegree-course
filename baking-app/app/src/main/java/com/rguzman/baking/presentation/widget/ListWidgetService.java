package com.rguzman.baking.presentation.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.rguzman.baking.domain.model.Recipe;

public class ListWidgetService extends RemoteViewsService {

    private static final String EXTRA_RECIPE = ListRemoteViewsFactory.EXTRA_RECIPE;

    public static Intent getIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, ListWidgetService.class);
        String recipeJson = new Gson().toJson(recipe);
        intent.putExtra(EXTRA_RECIPE, recipeJson);
        return intent;
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext(), intent);
    }

}

