package com.rguzman.techstore.presentation.product.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.rguzman.techstore.R;
import com.rguzman.techstore.presentation.product.detail.ProductDetailActivity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class NotifyWorker extends Worker {

  private final static String PRODUCT_NOTIFICATION_CHANNEL = "Techstore notification channel";

  private Data data;

  public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
    this.data = workerParams.getInputData();
  }

  @NonNull
  @Override
  public Result doWork() {
    buildNotifications();
    return Worker.Result.success();
  }

  private void buildNotifications() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int importance = NotificationManager.IMPORTANCE_DEFAULT;

      NotificationChannel channel =
              new NotificationChannel(PRODUCT_NOTIFICATION_CHANNEL, PRODUCT_NOTIFICATION_CHANNEL, importance);

      channel.setLightColor(Color.MAGENTA);

      NotificationManager notificationManager = (NotificationManager) getApplicationContext().
              getSystemService(Context.NOTIFICATION_SERVICE);
      if (notificationManager != null) {
        notificationManager.createNotificationChannel(channel);
      }
    }

    String productId = this.data.getString(ProductDetailActivity.DATA_PRODUCT_ID);
    String productName = this.data.getString(ProductDetailActivity.DATA_PRODUCT_NAME);

    Intent intent = ProductDetailActivity.getCallingIntent(getApplicationContext(), productId);

    PendingIntent pendingIntent =
            PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);

    String notificationTitle = getApplicationContext().getString(R.string.text_title_best_seller_product);

    NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(getApplicationContext(), PRODUCT_NOTIFICATION_CHANNEL)
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentTitle(notificationTitle)
                    .setContentText(productName)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


    NotificationManagerCompat notificationManager =
            NotificationManagerCompat.from(getApplicationContext());

    notificationManager.notify(1, notificationBuilder.build());
  }
}
