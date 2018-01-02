package com.ngocthach.taskmanager.di;

import com.ngocthach.taskmanager.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */

@Module
public class AppExecutorModule {

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

}
