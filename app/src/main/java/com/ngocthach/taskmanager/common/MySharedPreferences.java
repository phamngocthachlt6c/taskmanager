package com.ngocthach.taskmanager.common;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * ${CLASS}
 * Created by ThachPham on 26/12/2017.
 */

public class MySharedPreferences {

    private static final String SORT_TYPE = "sort_type";

    private SharedPreferences mSharedPreferences;

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void setSortType(int type) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(SORT_TYPE, type);
        editor.apply();
    }

    public int getSortType() {
        return mSharedPreferences.getInt(SORT_TYPE, Constants.TIMING);
    }
}
