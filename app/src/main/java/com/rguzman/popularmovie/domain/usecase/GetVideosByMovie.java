package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

import com.rguzman.popularmovie.data.repository.MovieRepository;
import com.rguzman.popularmovie.domain.model.Video;

import java.util.List;

import javax.inject.Inject;

public class GetVideosByMovie extends UseCase<Integer, List<Video>> {

    private final MovieRepository repository;

    @Inject
    public GetVideosByMovie(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(boolean forceUpdate, Integer params, Callback<List<Video>> callback) {
        this.repository.loadVideosByMovie(params, new Callback<List<Video>>() {
            @Override
            public void onNetworkResponse(LiveData<List<Video>> liveData) {
                callback.onNetworkResponse(liveData);
            }

            @Override
            public void onDiskResponse(LiveData<List<Video>> liveData) {

            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }
}
