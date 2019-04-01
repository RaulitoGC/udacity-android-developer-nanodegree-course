package com.rguzman.techstore.presentation.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.rguzman.techstore.R;

import dagger.android.support.DaggerAppCompatActivity;

public class CategoryListActivity extends DaggerAppCompatActivity {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CategoryListActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        setTitle(getString(R.string.app_name));
    }
}
