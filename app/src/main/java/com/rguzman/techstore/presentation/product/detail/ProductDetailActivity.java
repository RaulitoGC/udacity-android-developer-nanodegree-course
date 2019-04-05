package com.rguzman.techstore.presentation.product.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.rguzman.techstore.presentation.utils.ActivityUtils;

import dagger.android.support.DaggerAppCompatActivity;

public class ProductDetailActivity extends DaggerAppCompatActivity {

    public static final String EXTRA_PRODUCT_ID = "com.rguzman.techstore.extra.PRODUCT_ID";

    public static Intent getCallingIntent(Context context, String productId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().hasExtra(EXTRA_PRODUCT_ID)) {
            String productId = getIntent().getStringExtra(EXTRA_PRODUCT_ID);
            ActivityUtils.addFragment(this, android.R.id.content, ProductDetailFragment.newInstance(productId));
        }
    }
}
