package com.rguzman.techstore.data.database;

import com.rguzman.techstore.domain.model.Feature;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FeatureDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(List<Feature> features);

  @Query("SELECT * FROM Features WHERE productId = :productId")
  LiveData<List<Feature>> loadFeatures(String productId);

}
