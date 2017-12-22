package com.ngocthach.taskmanager.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.ngocthach.taskmanager.db.converter.DateConverter;

import java.util.Date;

/**
 * Created by ThachPham on 11/12/2017.
 */

@Entity(tableName = "tasks")
public class TaskEntity implements Parcelable {

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
    private int dayInWeek;

    public TaskEntity() {
    }

    public TaskEntity(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    protected TaskEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        typeOfTask = in.readInt();
        isNotification = in.readByte() != 0;
        isDone = in.readByte() != 0;
        priority = in.readInt();
        dayInWeek = in.readInt();
        date = new Date(in.readLong());
    }

    public static final Creator<TaskEntity> CREATOR = new Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel in) {
            return new TaskEntity(in);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };

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

    public int getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(int dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeInt(typeOfTask);
        parcel.writeByte((byte) (isNotification ? 1 : 0));
        parcel.writeByte((byte) (isDone ? 1 : 0));
        parcel.writeInt(priority);
        parcel.writeInt(dayInWeek);
        parcel.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void copy(TaskEntity taskEntity) {
        title = taskEntity.title;
        content = taskEntity.content;
        typeOfTask = taskEntity.typeOfTask;
        isNotification = taskEntity.isNotification;
        isDone = taskEntity.isDone;
        priority = taskEntity.priority;
        dayInWeek = taskEntity.dayInWeek;
        date = taskEntity.date;
    }
}
