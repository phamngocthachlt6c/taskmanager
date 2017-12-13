package com.ngocthach.taskmanager.viewmodel;

import android.arch.lifecycle.LiveData;

import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.List;

/**
 * Created by tryczson on 13/12/2017.
 */

public class TaskViewModel {

    private DataRepository dataRepository;
    private LiveData<List<TaskEntity>> listTask;

    public TaskViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        listTask = dataRepository.getTasks();
    }

    public LiveData<List<TaskEntity>> getListTask() {
        return listTask;
    }
}
