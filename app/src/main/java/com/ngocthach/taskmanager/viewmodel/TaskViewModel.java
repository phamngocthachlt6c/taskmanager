package com.ngocthach.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by ThachPham on 13/12/2017.
 */

public class TaskViewModel extends AndroidViewModel {

    private MediatorLiveData<Date> date;
    private DataRepository dataRepository;
    private LiveData<List<TaskEntity>> listTaskObserver;
    private List<TaskEntity> listTask = null;
    private MutableLiveData<List<TaskEntity>> list;

    public TaskViewModel(Application application/*, executor*/) {
        super(application);
        date = new MediatorLiveData<>();
        date.setValue(null);
        Log.d("aaaa", "TaskViewModel: getRepository");
        dataRepository = ((MyApplication) application).getRepository();

        listTaskObserver = Transformations.switchMap(date, input -> {
            Log.d("aaaaa", "TaskViewModel transformations: value = " + date.getValue());

            list = new MutableLiveData<List<TaskEntity>>();
            new Thread(() -> {
                listTask = dataRepository.getTasks(date.getValue());
                list.postValue(listTask);
            }).start();
            return list;
        });
    }

    public void deleteTask(int position) {
        listTask.remove(position);
    }

    public void insertList(TaskEntity taskEntity) {
        listTask.add(taskEntity);
        Log.d("aaaaaa", "insertList: listsize = " + listTask.size());
        list.setValue(listTask);
    }

    public void refreshList() {
        date.setValue(date.getValue());
    }

    public LiveData<List<TaskEntity>> getListTaskObserver() {
        return listTaskObserver;
    }

    public void setDate(Date date) {
        this.date.setValue(date);
    }

    public Date getValue() {
        return date.getValue();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TaskViewModel(mApplication);
        }
    }
}