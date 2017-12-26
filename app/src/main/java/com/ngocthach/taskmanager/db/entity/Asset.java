package com.ngocthach.taskmanager.db.entity;

import android.arch.persistence.room.PrimaryKey;

/**
 * ${CLASS}
 * Created by tryczson on 26/12/2017.
 */

/**
 * Chi dinh truoc mot so tai khoan, con lai de user tu add them
 */
public class Asset {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int amount;
}
