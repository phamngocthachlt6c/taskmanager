package com.ngocthach.taskmanager.di;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * ${CLASS}
 * Created by tryczson on 26/12/2017.
 */

@Module
public class SharedPreferencesModule {

    private Context context;

    public SharedPreferencesModule(Context context) {
        this.context = context;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences("name", Context.MODE_PRIVATE);
    }
}
