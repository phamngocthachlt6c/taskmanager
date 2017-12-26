package com.ngocthach.taskmanager.di;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * ${CLASS}
 * Created by tryczson on 26/12/2017.
 */

@Module
public class SharedPreferencesModule {

    @Inject
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("name", Context.MODE_PRIVATE);
    }
}
