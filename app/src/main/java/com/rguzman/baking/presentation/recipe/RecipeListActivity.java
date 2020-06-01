package com.rguzman.baking.presentation.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rguzman.baking.R;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeListActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        setTitle(R.string.text_recipe_title);
    }
}
