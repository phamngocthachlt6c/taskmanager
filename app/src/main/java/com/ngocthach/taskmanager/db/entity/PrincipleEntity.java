package com.ngocthach.taskmanager.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * ${CLASS}
 * Created by ThachPham on 26/12/2017.
 */
@Entity(tableName = "principles")
public class PrincipleEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int point;
    private String iconUrl;

    public PrincipleEntity() {
    }

    public PrincipleEntity(String title, int point, String iconUrl) {
        this.title = title;
        this.point = point;
        this.iconUrl = iconUrl;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
