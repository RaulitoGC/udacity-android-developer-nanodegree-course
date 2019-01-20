package com.rguzmanc.popularmovie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.rguzmanc.popularmovie.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private static final String BASE_MOVIE_DB_URL = "http://api.themoviedb.org/3/movie";

    public static final String PATH_MOST_POPULAR_MOVIES = "popular";

    public static final String PATH_HIGHEST_RATED_MOVIES = "top_rated";

    private final static String APY_KEY_QUERY_PARAM = "api_key";


    public static URL buildUrl(String path) {
        Uri builtUri = Uri.parse(BASE_MOVIE_DB_URL).buildUpon()
                .appendPath(path)
                .appendQueryParameter(APY_KEY_QUERY_PARAM, BuildConfig.MOVIE_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader in = null;
        try {

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
            return in.readLine();
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
