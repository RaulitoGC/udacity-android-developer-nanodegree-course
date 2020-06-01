package com.rguzmanc.popularmovie.asyntask;

import android.content.Context;
import android.os.AsyncTask;

import com.rguzmanc.popularmovie.exception.NetworkConnectionException;
import com.rguzmanc.popularmovie.model.Movie;
import com.rguzmanc.popularmovie.utils.MovieJsonUtils;
import com.rguzmanc.popularmovie.utils.NetworkUtils;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

public class FetchPopularMoviesAsynTask extends AsyncTask<String, Void, List<Movie>> {

    public interface LoadMoviesCallback {
        void onLoadSuccess(List<Movie> movies);

        void onLoadError(Exception exception);
    }

    private final WeakReference<Context> context;
    private final LoadMoviesCallback callback;
    private Exception exception = new Exception();

    public FetchPopularMoviesAsynTask(Context context, LoadMoviesCallback callback) {
        this.context = new WeakReference<>(context);
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        if (!NetworkUtils.isNetworkAvailable(context.get())) {
            callback.onLoadError(new NetworkConnectionException());
            cancel(true);
        }
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        String path = params[0];
        URL moviesRequestUrl = NetworkUtils.buildUrl(path);

        try {
            String jsonMoviesResponse = NetworkUtils
                    .getResponseFromHttpUrl(moviesRequestUrl);

            return MovieJsonUtils.getListMoviesFromJson(context.get(), jsonMoviesResponse);

        } catch (Exception exception) {
            this.exception = exception;
            exception.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            callback.onLoadSuccess(movies);
        } else {
            callback.onLoadError(exception);
        }
    }
}
