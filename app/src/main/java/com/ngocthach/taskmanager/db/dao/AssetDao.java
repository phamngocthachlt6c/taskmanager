package com.ngocthach.taskmanager.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ngocthach.taskmanager.db.entity.AssetEntity;

import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 28/12/2017.
 */

@Dao
public interface AssetDao {
    @Query("SELECT * FROM assets")
    LiveData<List<AssetEntity>> loadAllAssets();

    @Query("SELECT * FROM assets WHERE id = :id")
    LiveData<AssetEntity> loadAsset(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAsset(AssetEntity asset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListAsset(List<AssetEntity> assets);

    @Update
    int updateAsset(AssetEntity asset);

    @Delete
    void deleteAsset(AssetEntity asset);
}
