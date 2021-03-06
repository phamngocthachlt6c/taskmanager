/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ngocthach.taskmanager.db;

import com.ngocthach.taskmanager.db.entity.AssetEntity;
import com.ngocthach.taskmanager.db.entity.BookEntity;
import com.ngocthach.taskmanager.db.entity.PrincipleEntity;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    public static List<TaskEntity> generateTasks() {
        List<TaskEntity> tasks = new ArrayList<>();
        tasks.add(new TaskEntity("task1", new Date(2017, 12, 11, 12, 12, 12)));
        tasks.add(new TaskEntity("task2", new Date(2017, 12, 12, 12, 12, 12)));
        tasks.add(new TaskEntity("task3", new Date(2017, 12, 13, 12, 12, 12)));
        tasks.add(new TaskEntity("task4", new Date(2017, 12, 13, 12, 12, 12)));
        return tasks;
    }

    public static List<AssetEntity> generateAssets() {
        List<AssetEntity> assets = new ArrayList<>();
        assets.add(new AssetEntity("Long tot", "", 1, 1, "url"));
        assets.add(new AssetEntity("Long tot", "", 1, 1, "url"));
        assets.add(new AssetEntity("Long tot", "", 1, 1, "url"));
        assets.add(new AssetEntity("Long tot", "", 1, 1, "url"));
        return assets;
    }

    public static List<PrincipleEntity> generatePrinciples() {
        List<PrincipleEntity> principles = new ArrayList<>();
        principles.add(new PrincipleEntity("An nhieu", 100, "url"));
        principles.add(new PrincipleEntity("An nhieu", 100, "url"));
        principles.add(new PrincipleEntity("An nhieu", 100, "url"));
        principles.add(new PrincipleEntity("An nhieu", 100, "url"));
        return principles;
    }

    public static List<BookEntity> generateBooks() {
        List<BookEntity> bookEntities = new ArrayList<>();
        bookEntities.add(new BookEntity("An nhieu", "thach", false));
        bookEntities.add(new BookEntity("An nhieu", "thach", false));
        bookEntities.add(new BookEntity("An nhieu", "thach", false));
        bookEntities.add(new BookEntity("An nhieu", "thach", false));
        return bookEntities;
    }
}
