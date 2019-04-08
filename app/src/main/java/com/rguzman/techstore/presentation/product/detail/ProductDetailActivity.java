package com.rguzman.techstore.presentation.product.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.presentation.product.notification.NotifyWorker;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class ProductDetailActivity extends DaggerAppCompatActivity implements ProductDetailView {

  public static final String EXTRA_PRODUCT_ID = "com.rguzman.techstore.extra.PRODUCT_ID";
  public static final String NOTIFICATION_WORK_MANAGER_TAG = "Work_Manager";
  public static final String DATA_PRODUCT_ID = "com.rguzman.techstore.data.PRODUCT_ID";
  public static final String DATA_PRODUCT_NAME = "com.rguzman.techstore.data.PRODUCT_NAME";

  @BindView(R.id.product_image)
  AppCompatImageView productImg;

  @BindView(R.id.txt_product_description)
  AppCompatTextView txtProductDescription;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  @BindView(R.id.txt_amount)
  AppCompatTextView txtAmount;

  @BindView(R.id.progressContainer)
  View progressBarContainer;

  @Inject
  ViewModelProvider.Factory viewModelFactory;
  private ProductDetailViewModel productDetailViewModel;
  private FeatureAdapter featureAdapter;
  private int amount;

  public static Intent getCallingIntent(Context context, String productId) {
    Intent intent = new Intent(context, ProductDetailActivity.class);
    intent.putExtra(EXTRA_PRODUCT_ID, productId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_detail);
    ButterKnife.bind(this);

    supportPostponeEnterTransition();

    if (getIntent() != null && getIntent().hasExtra(EXTRA_PRODUCT_ID)) {
      String productId = getIntent().getStringExtra(EXTRA_PRODUCT_ID);

      this.productDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailViewModel.class);
      this.productDetailViewModel.setView(this);
      this.productDetailViewModel.init(productId);
    }
    initUi();
  }

  private void initUi() {
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("");

    final LinearLayoutManager linearLayout = new LinearLayoutManager(this);
    this.recyclerView.setLayoutManager(linearLayout);
    this.recyclerView.setHasFixedSize(true);

    this.featureAdapter = new FeatureAdapter();
    this.recyclerView.setAdapter(featureAdapter);

    this.amount = 1;
    updateAmount(String.valueOf(amount));
  }

  @Override
  public void loadProduct(Product product) {

    supportPostponeEnterTransition();

    Objects.requireNonNull(getSupportActionBar()).setTitle(product.getName().substring(0, 20));
    txtProductDescription.setText(product.getDescription());
    Glide.with(this)
            .load(product.getImage())
            .centerCrop()
            .dontAnimate()
            .listener(new RequestListener<Drawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                supportStartPostponedEnterTransition();
                return false;
              }

              @Override
              public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                supportStartPostponedEnterTransition();
                return false;
              }
            })
            .into(productImg);

    scheduleNotification(product.getProductId(), product.getName());
  }

  @Override
  public void loadFeatures(List<Feature> features) {
    featureAdapter.setList(features);
  }

  @Override
  public Context context() {
    return this;
  }

  @Override
  public void showError(String message) {
    Toast.makeText(context(), message, Toast.LENGTH_LONG).show();
  }

  @OnClick(R.id.amount_less_button)
  public void onAmountLessButtonClick() {
    if (amount != 1) {
      this.amount--;
    }
    updateAmount(String.valueOf(amount));
  }

  @OnClick(R.id.amount_plus_button)
  public void onAmountPlusButtonClick() {
    this.amount++;
    updateAmount(String.valueOf(amount));
  }

  private void updateAmount(String amount) {
    this.txtAmount.setText(amount);
  }

  @OnClick(R.id.buy_button)
  public void onBuyButtonClick() {
    progressBarContainer.setVisibility(View.VISIBLE);
    new Handler().postDelayed(() -> {
      progressBarContainer.setVisibility(View.GONE);
      showSuccessDialog();
    }, 3000);
  }

  private void showSuccessDialog() {
    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    alertDialog.setTitle(getString(R.string.text_title_sucess_buy));
    alertDialog.setMessage(getString(R.string.text_message_success_buy));
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            (dialog, which) -> {
              dialog.dismiss();
              finishAfterTransition();
            });
    alertDialog.show();
  }

  private void scheduleNotification(String productId, String productName) {

    final Data.Builder productData = new Data.Builder();

    productData.putString(DATA_PRODUCT_ID, productId);
    productData.putString(DATA_PRODUCT_NAME, productName);

    long SCHEDULE_TIME_NOTIFICATION = 20000;
    OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotifyWorker.class)
            .setInitialDelay(SCHEDULE_TIME_NOTIFICATION, TimeUnit.MILLISECONDS)
            .setInputData(productData.build())
            .addTag(NOTIFICATION_WORK_MANAGER_TAG)
            .build();

    WorkManager.getInstance().beginUniqueWork(NOTIFICATION_WORK_MANAGER_TAG, ExistingWorkPolicy.REPLACE, notificationWork).enqueue();
  }
}
