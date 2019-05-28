package com.rguzman.techstore.data.repository.category.datasource.disk;

import androidx.lifecycle.LiveData;

import com.rguzman.techstore.data.database.AppDatabase;
import com.rguzman.techstore.domain.model.Category;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CategoryDiskDataSourceImpl implements CategoryDiskDataSource {

  private final AppDatabase appDatabase;
  private final Executor diskExecutor;

  @Inject
  public CategoryDiskDataSourceImpl(AppDatabase appDatabase, Executor diskExecutor) {
    this.appDatabase = appDatabase;
    this.diskExecutor = diskExecutor;
  }

  @Override
  public void saveCategories(List<Category> categories) {
    if (categories != null) {
      diskExecutor.execute(() -> {
        for (Category category : categories) {
          Category localMovie = appDatabase.categoryDao().getMovieById(category.getId());
          if (localMovie != null) {
            category.setId(localMovie.getId());
          }
        }
        insertOrUpdate(categories);
      });
    }
  }

  @Override
  public LiveData<List<Category>> loadCategories() {
    return appDatabase.categoryDao().loadCategories();
  }

  @Override
  public void cleanCategories() {
    diskExecutor.execute(() -> {
      this.appDatabase.categoryDao().deleteCategories();
    });

  }

  private void insertOrUpdate(List<Category> categories) {
    appDatabase.categoryDao().insert(categories);
  }
}
