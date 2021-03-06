/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ngocthach.taskmanager.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.ngocthach.taskmanager.AppExecutors;
import com.ngocthach.taskmanager.db.dao.AssetDao;
import com.ngocthach.taskmanager.db.dao.BookDao;
import com.ngocthach.taskmanager.db.dao.PrincipleDao;
import com.ngocthach.taskmanager.db.dao.TaskDao;
import com.ngocthach.taskmanager.db.entity.AssetEntity;
import com.ngocthach.taskmanager.db.entity.BookEntity;
import com.ngocthach.taskmanager.db.entity.PrincipleEntity;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.List;

@Database(entities = {TaskEntity.class, AssetEntity.class, PrincipleEntity.class, BookEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "basic-sample-db";

    public abstract TaskDao taskDao();

    public abstract AssetDao assetDao();

    public abstract PrincipleDao principleDao();

    public abstract BookDao bookDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
            final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            // Generate the data for pre-population
                            Log.d("aaaaa", "onCreate: generate data");
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<TaskEntity> task = DataGenerator.generateTasks();
                            insertTaskData(database, task);

                            List<AssetEntity> assets = DataGenerator.generateAssets();
                            insertAssetData(database, assets);

                            List<PrincipleEntity> principles = DataGenerator.generatePrinciples();
                            insertPrincipleData(database, principles);

                            List<BookEntity> books = DataGenerator.generateBooks();
                            insertBookData(database, books);

                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertTaskData(final AppDatabase database, final List<TaskEntity> tasks) {
        database.runInTransaction(() -> {
            database.taskDao().insertAll(tasks);
        });
    }

    private static void insertAssetData(final AppDatabase database, final List<AssetEntity> assets) {
        database.runInTransaction(() -> {
            database.assetDao().insertListAsset(assets);
        });
    }

    private static void insertPrincipleData(final AppDatabase database, final List<PrincipleEntity> principleEntities) {
        database.runInTransaction(() -> {
            database.principleDao().insertListPrinciple(principleEntities);
        });
    }

    private static void insertBookData(final AppDatabase database, final List<BookEntity> bookEntities) {
        database.runInTransaction(() -> {
            database.bookDao().insertListBook(bookEntities);
        });
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
