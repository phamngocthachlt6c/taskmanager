package com.ngocthach.taskmanager.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.ngocthach.taskmanager.db.converter.DateConverter;
import com.ngocthach.taskmanager.model.Task;

import java.util.Date;

/**
 * Created by ThachPham on 11/12/2017.
 */

@Entity(tableName = "tasks")
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;
    private int typeOfTask;
    private boolean isNotification;
    private boolean isDone;
    private int priority;

    @TypeConverters({DateConverter.class})
    private Date date;

    public TaskEntity() {
    }

    public TaskEntity(String title, Date date) {
        this.title = title;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTypeOfTask() {
        return typeOfTask;
    }

    public void setTypeOfTask(int typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setNotification(boolean notification) {
        isNotification = notification;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
