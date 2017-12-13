package com.ngocthach.taskmanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.List;

/**
 * Created by ThachPham on 13/12/2017.
 */

public class TaskViewModel extends ViewModel {

    private MutableLiveData<String> date = new MutableLiveData<>();
    private DataRepository dataRepository;
    private LiveData<List<TaskEntity>> listTask;

    public TaskViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        listTask = Transformations.switchMap(date, input -> dataRepository.getTasks());
    }

    public LiveData<List<TaskEntity>> getListTask() {
        return listTask;
    }

    public void setDate(String date) {
        this.date.setValue(date);
    }
}
