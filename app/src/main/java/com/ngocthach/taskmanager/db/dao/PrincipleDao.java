package com.ngocthach.taskmanager.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ngocthach.taskmanager.db.entity.PrincipleEntity;

import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */
@Dao
public interface PrincipleDao {
    @Query("SELECT * FROM principles")
    LiveData<List<PrincipleEntity>> loadAllPrinciples();

    @Query("SELECT * FROM principles WHERE id = :id")
    LiveData<PrincipleEntity> loadPrinciple(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPrinciple(PrincipleEntity principle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListPrinciple(List<PrincipleEntity> principles);

    @Update
    int updatePrinciple(PrincipleEntity principle);

    @Delete
    void deletePrinciple(PrincipleEntity principle);
}
