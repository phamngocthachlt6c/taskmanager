package com.ngocthach.taskmanager.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.List;

/**
 * Created by ThachPham on 11/12/2017.
 */
@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> loadAllTasks();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskEntity> loadTask(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaskEntity> tasks);

}
