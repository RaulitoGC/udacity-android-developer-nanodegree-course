package com.rguzman.techstore.presentation.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.rguzman.techstore.R;

import dagger.android.support.DaggerAppCompatActivity;

public class CategoryListActivity extends DaggerAppCompatActivity {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, CategoryListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        setTitle(getString(R.string.app_name));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
