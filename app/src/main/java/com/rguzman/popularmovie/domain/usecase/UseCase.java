package com.rguzman.popularmovie.domain.usecase;

import android.arch.lifecycle.LiveData;

public abstract class UseCase<P, R> {

    public abstract LiveData<R> execute(P params);

}
