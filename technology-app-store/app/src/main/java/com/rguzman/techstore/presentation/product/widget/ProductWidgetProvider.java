package com.rguzman.techstore.presentation.product.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.rguzman.techstore.R;
import com.rguzman.techstore.data.preferences.ProductPrefs;
import com.rguzman.techstore.data.preferences.ProductPrefsImpl;
import com.rguzman.techstore.domain.model.Product;
import com.rguzman.techstore.presentation.product.detail.ProductDetailActivity;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class ProductWidgetProvider extends AppWidgetProvider {

  public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                     int appWidgetId) {


    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.last_product_buy_widget);

    ProductPrefs productPrefs = new ProductPrefsImpl(PreferenceManager.getDefaultSharedPreferences(context));

    Product product = productPrefs.getProduct();

    Intent intent = ProductDetailActivity.getCallingIntent(context, product.getProductId());

    PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    Timber.d("title" + product.getName());
    Timber.d(" price" + product.getPrice());
    views.setTextViewText(R.id.txt_widget_product_title, product.getName());
    views.setTextViewText(R.id.txt_widget_product_price, product.getPrice());
    views.setOnClickPendingIntent(R.id.widget_root, pendingIntent);

    appWidgetManager.updateAppWidget(appWidgetId, views);

  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

