package com.rguzman.techstore.domain.usecase;

import java.util.concurrent.Executor;

public abstract class UseCase<P, R> {
  private boolean forceCache;

  protected final Executor uiExecutor;

  public UseCase(Executor uiExecutor) {
    this.uiExecutor = uiExecutor;
  }

  public void execute(boolean forceCache, P params, UseCaseCallback<R> callback) {

  }

  public void execute(P params, UseCaseCallback<R> callback) {
    this.execute(false, params, callback);
  }

  public void execute(UseCaseCallback<R> callback) {
    this.execute(false, null, callback);
  }

  public void execute(P params) {
    this.execute(false, params, null);
  }

  public void execute(boolean forceCache, UseCaseCallback<R> callback) {
    this.execute(forceCache, null, callback);
  }

  public boolean isForceCache() {
    return forceCache;
  }

  public void setForceCache(boolean forceCache) {
    this.forceCache = forceCache;
  }

}
