package com.rguzman.popularmovie.data.repository.datasource.disk;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.rguzman.popularmovie.data.database.AppDatabase;
import com.rguzman.popularmovie.data.exception.GenericException;
import com.rguzman.popularmovie.domain.model.Movie;
import com.rguzman.popularmovie.domain.usecase.DataWrapper;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


@Singleton
public class MovieDiskDataSource implements DiskDataSource {

    private final AppDatabase appDatabase;
    private final Executor diskExecutor;

    @Inject
    public MovieDiskDataSource(AppDatabase appDatabase, Executor diskExecutor) {
        this.appDatabase = appDatabase;
        this.diskExecutor = diskExecutor;
    }

    @Override
    public LiveData<List<Movie>> getMovies(String path) {
        Timber.d(" Get movides from data base");
        final MutableLiveData<DataWrapper<List<Movie>>> liveData = new MutableLiveData<>();
        final DataWrapper<List<Movie>> dataWrapper = new DataWrapper<>();
        MovieDao dao = appDatabase.movieDao();
        LiveData<List<Movie>> listLiveData = dao.loadAllMovies();

        if (listLiveData != null && listLiveData.getValue() != null) {
            dataWrapper.setData(listLiveData.getValue());
        } else {
            dataWrapper.setException(new GenericException());
        }
        liveData.setValue(dataWrapper);
        return dao.loadAllMovies();
    }

    @Override
    public void saveMovies(List<Movie> movies) {
        if (movies != null) {
            diskExecutor.execute(() -> {
                MovieDao movieDao = appDatabase.movieDao();
                for (Movie movie : movies) {
                    movieDao.insert(movie);
                }
            });
        }
    }
}
