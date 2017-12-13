package com.ngocthach.taskmanager.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tryczson on 11/12/2017.
 */

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey
    private int id;
    private String title;

    public TaskEntity(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
