package com.rguzman.popularmovie.presentation.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static boolean isThereNetworkConnection(Context context) {
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =
                connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        return isConnected;
    }
}
