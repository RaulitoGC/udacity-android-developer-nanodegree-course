package com.rguzman.techstore.presentation.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.rguzman.techstore.presentation.utils.ActivityUtils;

import dagger.android.support.DaggerAppCompatActivity;

public class ProductListActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "com.rguzman.techstore.extra.CATEGORY_ID";

    public static Intent getCallingIntent(Context context, String categoryId) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().hasExtra(EXTRA_CATEGORY_ID)) {
            String categoryId = getIntent().getStringExtra(EXTRA_CATEGORY_ID);
            ActivityUtils.addFragment(this, android.R.id.content, ProductListFragment.newInstance(categoryId));
        }
    }
}
