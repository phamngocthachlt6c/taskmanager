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

package com.ngocthach.taskmanager;

import android.app.Application;

import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.di.AppComponent;
import com.ngocthach.taskmanager.di.AssetViewModelModule;
import com.ngocthach.taskmanager.di.ContextModule;
import com.ngocthach.taskmanager.di.DaggerAppComponent;
import com.ngocthach.taskmanager.di.SharedPreferencesModule;

/**
 * Android Application class. Used for accessing singletons.
 */
public class MyApplication extends Application {

    private AppExecutors mAppExecutors;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .sharedPreferencesModule(new SharedPreferencesModule())
                .assetViewModelModule(new AssetViewModelModule(this))
                .build();

    }

    public AppComponent getMyComponent() {
        return appComponent;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }

}
