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
}
