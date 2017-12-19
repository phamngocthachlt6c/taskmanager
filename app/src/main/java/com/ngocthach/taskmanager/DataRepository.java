package com.ngocthach.taskmanager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.util.Log;


import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<TaskEntity>> getTasks() {
        return mDatabase.taskDao().loadAllTasks();
    }

    public LiveData<List<TaskEntity>> getTasks(Date date) {
        if(date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date dateTime1 = new Date(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date dateTime2 = new Date(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Log.d("aaaaaa", "getTasks: dateTime2 = " + dateTime2);
        return mDatabase.taskDao().loadTasks(dateTime1.getTime(), dateTime2.getTime());
    }

    public LiveData<TaskEntity> loadTask(final int productId) {
        return mDatabase.taskDao().loadTask(productId);
    }

    public void insertTask(TaskEntity taskEntity) {
        Log.d("aaaaa", "insertTask: insert task dao");
        mDatabase.taskDao().insertAll(Arrays.asList(taskEntity));
    }
}
