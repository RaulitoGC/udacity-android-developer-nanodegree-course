package com.rguzman.baking.presentation.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.rguzman.baking.domain.model.Ingredient;

import java.util.ArrayList;

public class ListWidgetService extends RemoteViewsService {

    private static final String EXTRA_LIST_INGREDIENTS = ListRemoteViewsFactory.EXTRA_LIST_INGREDIENTS;

    public static Intent getIntent(Context context, ArrayList<Ingredient> ingredients) {
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putParcelableArrayListExtra(EXTRA_LIST_INGREDIENTS, ingredients);
        return intent;
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext(), intent);
    }
}

