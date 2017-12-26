package com.ngocthach.taskmanager.common;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * ${CLASS}
 * Created by tryczson on 26/12/2017.
 */

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void putData() {

    }
}
