package com.rguzman.baking.presentation.recipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.rguzman.baking.R;
import com.rguzman.baking.presentation.IdlingResource.SimpleIdlingResource;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeListActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        setTitle(R.string.text_recipe_title);
    }
}
