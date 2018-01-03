package com.ngocthach.taskmanager.common;

/**
 * Created by ThachPham on 19/12/2017.
 */

public class Constants {

    // --------- TASK ENTITY---------------//
    public static class TaskEntity {
        // Type of task
        public static final int DAILY_TASK = 0;
        public static final int SINGLE_DAY_TASK = 1;
        // Priority
        public static final int HIGHT_PRIOR =0;
        public static final int MEDIUM_PRIOR =1;
        public static final int LOW_PRIOR =2;
    }

    // --------- ADD TASK -----------------//
    public static final int ADD_TASK_REQUEST = 0;
    public static final int ADD_TASK_SUCCESS = 1;
    public static final int ADD_TASK_FAILED = 2;

    // ---------- EDIT TASK --------------//
    public static final int EDIT_TASK_REQUEST = 3;
    public static final int EDIT_TASK_SUCCESS = 4;
    public static final int EDIT_TASK_FAILED = 5;

    // ---------- SORT TYPE ---------------//
    public static final int TIMING = 0;
    public static final int PRIORITY = 1;

    // ----------- PATH -------------------//
    public static final String DEFAULT_TASK_ICON_PATH = "file:///android_asset/icon_tasks/icon_task_1.png";
    public static final String DEFAULT_ASSET_ICON_PATH = "file:///android_asset/icon_assets/treasure1.png";
    public static final String DEFAULT_PRINCIPLE_ICON_PATH = "file:///android_asset/icon_principles/p1.png";
    public static final String ASSETS_FOLDER_PATH = "file:///android_asset/";

    // ----------- TAB TYPE -----------------//
    public static final int TAB_HOME = 0;
    public static final int TAB_ASSETS = 1;
    public static final int TAB_PRINCIPLE = 2;
    public static final int TAB_REPORT = 3;

}
