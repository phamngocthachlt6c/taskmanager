package com.ngocthach.taskmanager.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * ${CLASS}
 * Created by ThachPham on 26/12/2017.
 */

/**
 * Chi dinh truoc mot so tai khoan, con lai de user tu add them
 */
@Entity(tableName = "assets")
public class AssetEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String assetContent;
    private int typeOfAsset;
    private int value;
    private String iconUrl;

    public AssetEntity(String name, String assetContent, int typeOfAsset, int value, String iconUrl) {
        this.name = name;
        this.assetContent = assetContent;
        this.typeOfAsset = typeOfAsset;
        this.value = value;
        this.iconUrl = iconUrl;
    }

    public AssetEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetContent() {
        return assetContent;
    }

    public void setAssetContent(String assetContent) {
        this.assetContent = assetContent;
    }

    public int getTypeOfAsset() {
        return typeOfAsset;
    }

    public void setTypeOfAsset(int typeOfAsset) {
        this.typeOfAsset = typeOfAsset;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
