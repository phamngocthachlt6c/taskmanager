package com.ngocthach.taskmanager;

import android.arch.lifecycle.LiveData;
import android.util.Log;


import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.AssetEntity;
import com.ngocthach.taskmanager.db.entity.BookEntity;
import com.ngocthach.taskmanager.db.entity.PrincipleEntity;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

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

    public List<TaskEntity> getTasks(Date date) {
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

    public long insertTask(TaskEntity taskEntity) {
        Log.d("aaaaa", "insertTask: insert task dao");
        return mDatabase.taskDao().insertTask(taskEntity);
    }

    public int updateTask(TaskEntity taskEntity) {
        return mDatabase.taskDao().updateTask(taskEntity);
    }

    public void deleteTask(TaskEntity taskEntity) {
        mDatabase.taskDao().deleteTask(taskEntity);
    }

    public LiveData<List<AssetEntity>> getAssets() {
        return mDatabase.assetDao().loadAllAssets();
    }

    public long insertAsset(AssetEntity assetEntity) {
        return mDatabase.assetDao().insertAsset(assetEntity);
    }

    public void deleteAsset(AssetEntity assetEntity) {
        mDatabase.assetDao().deleteAsset(assetEntity);
    }

    public void updateAsset(AssetEntity assetEntity) {
        mDatabase.assetDao().updateAsset(assetEntity);
    }

    public LiveData<List<PrincipleEntity>> getPrinciples() {
        return mDatabase.principleDao().loadAllPrinciples();
    }

    public void deletePrinciple(PrincipleEntity principle) {
        mDatabase.principleDao().deletePrinciple(principle);
    }

    public long insertPrinciple(PrincipleEntity entity) {
        return mDatabase.principleDao().insertPrinciple(entity);
    }

    public void updatePrinciple(PrincipleEntity entity) {
        mDatabase.principleDao().updatePrinciple(entity);
    }

    public LiveData<List<BookEntity>> getBooks() {
        return mDatabase.bookDao().loadAllBooks();
    }

    public void insertBook(BookEntity bookEntity) {
        mDatabase.bookDao().insertBook(bookEntity);
    }

    public void updateBook(BookEntity bookEntity) {
        mDatabase.bookDao().updateBook(bookEntity);
    }

    public void deleteBook(BookEntity bookEntity) {
        mDatabase.bookDao().deleteBook(bookEntity);
    }
}
