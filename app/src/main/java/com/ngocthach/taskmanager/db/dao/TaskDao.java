package com.ngocthach.taskmanager.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.Date;
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

    @Query("SELECT * FROM tasks WHERE date BETWEEN :time1 AND :time2 ORDER BY date")
    List<TaskEntity> loadTasks(long time1, long time2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaskEntity> tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTask(TaskEntity task);

    @Update
    int updateTask(TaskEntity task);

    @Delete
    void deleteTask(TaskEntity task);

}
