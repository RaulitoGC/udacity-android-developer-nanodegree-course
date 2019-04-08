package com.rguzman.techstore.presentation.product.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.rguzman.techstore.data.preferences.ProductPrefs;
import com.rguzman.techstore.domain.model.Product;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class LastProductBoughtIntentService extends IntentService {

  @Inject
  ProductPrefs productPrefs;

  public static final String LAST_PRODUCT_BOUGHT_SERVICE = "last_product_bought_service";
  public static final String ACTION_LAST_PRODUCT_BOUGHT = "com.rguzman.techstore.widgets.lastProductBought";
  public static final String EXTRA_PRODUCT_ID = "com.rguzman.techstore.widgets.PRODUCT_ID";
  public static final String EXTRA_PRODUCT_NAME = "com.rguzman.techstore.widgets.PRODUCT_NAME";
  public static final String EXTRA_PRODUCT_IMAGE = "com.rguzman.techstore.widgets.PRODUCT_IMAGE";
  public static final String EXTRA_PRODUCT_PRICE = "com.rguzman.techstore.widgets.PRODUCT_PRICE";
  public static final String EXTRA_PRODUCT_CATEGORY_ID = "com.rguzman.techstore.widgets.PRODUCT_CATEGORY_ID";
  public static final String EXTRA_PRODUCT_DESCRIPTION = "com.rguzman.techstore.widgets.PRODUCT_DESCRIPTION";

  public static Intent getCallingIntent(Context context, Product product) {
    Intent intent = new Intent(context, LastProductBoughtIntentService.class);
    intent.putExtra(EXTRA_PRODUCT_ID, product.getProductId());
    intent.putExtra(EXTRA_PRODUCT_NAME, product.getName());
    intent.putExtra(EXTRA_PRODUCT_IMAGE, product.getImage());
    intent.putExtra(EXTRA_PRODUCT_PRICE, product.getPrice());
    intent.putExtra(EXTRA_PRODUCT_CATEGORY_ID, product.getCategoryId());
    intent.putExtra(EXTRA_PRODUCT_DESCRIPTION, product.getDescription());
    intent.setAction(ACTION_LAST_PRODUCT_BOUGHT);
    return intent;
  }

  public LastProductBoughtIntentService() {
    super(LAST_PRODUCT_BOUGHT_SERVICE);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    if (intent != null) {
      final String action = intent.getAction();

      if (ACTION_LAST_PRODUCT_BOUGHT.equals(action) &&
              intent.hasExtra(EXTRA_PRODUCT_ID) &&
              intent.hasExtra(EXTRA_PRODUCT_NAME) &&
              intent.hasExtra(EXTRA_PRODUCT_DESCRIPTION) &&
              intent.hasExtra(EXTRA_PRODUCT_CATEGORY_ID) &&
              intent.hasExtra(EXTRA_PRODUCT_IMAGE) &&
              intent.hasExtra(EXTRA_PRODUCT_PRICE)) {

        Product product = new Product();

        final String productId = intent.getStringExtra(EXTRA_PRODUCT_ID);
        final String productName = intent.getStringExtra(EXTRA_PRODUCT_NAME);
        final String productDescription = intent.getStringExtra(EXTRA_PRODUCT_DESCRIPTION);
        final String productImage = intent.getStringExtra(EXTRA_PRODUCT_IMAGE);
        final String productPrice = intent.getStringExtra(EXTRA_PRODUCT_PRICE);
        final String productCategoryId = intent.getStringExtra(EXTRA_PRODUCT_CATEGORY_ID);

        product.setProductId(productId);
        product.setName(productName);
        product.setDescription(productDescription);
        product.setImage(productImage);
        product.setPrice(productPrice);
        product.setCategoryId(productCategoryId);

        updateLastProductBought(product);
      }
    }
  }

  private void updateLastProductBought(Product product) {
    productPrefs.saveProduct(product);

    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ProductWidgetProvider.class));
    for (int appWidgetId : widgetIds) {
      ProductWidgetProvider.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId);
    }
  }
}
