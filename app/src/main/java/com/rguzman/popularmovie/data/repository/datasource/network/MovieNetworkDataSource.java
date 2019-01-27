package com.rguzman.popularmovie.data.repository.datasource.network;


import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.rguzman.popularmovie.BuildConfig;
import com.rguzman.popularmovie.data.exception.NetworkConnectionException;
import com.rguzman.popularmovie.data.repository.datasource.network.response.MovieListResponse;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.DataWrapper;
import com.rguzman.popularmovie.presentation.utils.NetworkUtils;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class MovieNetworkDataSource implements NetworkDataSource {

    private final ApiService apiService;
    private final Context context;
    private final Executor networkExecutor;

    @Inject
    public MovieNetworkDataSource(ApiService apiService, Context context, Executor networkExecutor) {
        this.apiService = apiService;
        this.context = context;
        this.networkExecutor = networkExecutor;
    }

    @Override
    public void loadMovies(String path, MovieNetworkCallback callback) {
        final MutableLiveData<DataWrapper<List<Movie>>> liveData = new MutableLiveData<>();
        final DataWrapper<List<Movie>> dataWrapper = new DataWrapper<>();

        if (!NetworkUtils.isThereNetworkConnection(context)) {
            dataWrapper.setException(new NetworkConnectionException());
            liveData.setValue(dataWrapper);
        } else {


            Timber.d(" get data from network");
            Call<MovieListResponse> call = apiService.getMovies(path, BuildConfig.MOVIE_API_KEY);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                    if (response.isSuccessful()) {
                        dataWrapper.setData(response.body().getResults());
                        liveData.setValue(dataWrapper);
                        callback.onResponse(liveData);
                    } else {
                        dataWrapper.setException(new Exception(response.body().getMessage()));
                        liveData.setValue(dataWrapper);
                    }
                }

                @Override
                public void onFailure(Call<MovieListResponse> call, Throwable t) {
                    t.printStackTrace();
                    dataWrapper.setException(new Exception(t));
                    liveData.setValue(dataWrapper);
                }
            });

        }

    }

    public interface MovieNetworkCallback {
        void onResponse(MutableLiveData<DataWrapper<List<Movie>>> liveData);
    }

}
