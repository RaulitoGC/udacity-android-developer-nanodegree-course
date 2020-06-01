package com.rguzman.techstore.presentation.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rguzman.techstore.presentation.product.notification.NotifyWorker;
import com.rguzman.techstore.presentation.utils.ActivityUtils;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import dagger.android.support.DaggerAppCompatActivity;

public class ProductListActivity extends DaggerAppCompatActivity {

  private static final String EXTRA_CATEGORY_ID = "com.rguzman.techstore.extra.CATEGORY_ID";
  private static final String EXTRA_CATEGORY_NAME = "com.rguzman.techstore.extra.CATEGORY_NAME";


  public static Intent getCallingIntent(Context context, String categoryId, String categoryName) {
    Intent intent = new Intent(context, ProductListActivity.class);
    intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
    intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getIntent() != null && getIntent().hasExtra(EXTRA_CATEGORY_ID) && getIntent().hasExtra(EXTRA_CATEGORY_NAME)) {
      String categoryId = getIntent().getStringExtra(EXTRA_CATEGORY_ID);
      String title = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
      setTitle(title);
      ActivityUtils.addFragment(this, android.R.id.content, ProductListFragment.newInstance(categoryId));
    }
  }


}
