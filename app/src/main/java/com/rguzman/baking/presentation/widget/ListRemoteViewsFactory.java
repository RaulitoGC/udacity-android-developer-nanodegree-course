package com.rguzman.baking.presentation.widget;

import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rguzman.baking.domain.model.Recipe;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Recipe recipe;

    public ListRemoteViewsFactory(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
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
