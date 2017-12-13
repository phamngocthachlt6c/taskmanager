package com.ngocthach.taskmanager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;


import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.List;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<TaskEntity>> mObservableTasks;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableTasks = new MediatorLiveData<>();

        mObservableTasks.addSource(mDatabase.taskDao().loadAllTasks(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableTasks.postValue(productEntities);
                    }
                });
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
        return mObservableTasks;
    }

    public LiveData<TaskEntity> loadTask(final int productId) {
        return mDatabase.taskDao().loadTask(productId);
    }
}
