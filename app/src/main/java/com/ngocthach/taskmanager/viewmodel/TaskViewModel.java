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
    private LiveData<List<TaskEntity>> listTask;
    MutableLiveData<List<TaskEntity>> list;

    public TaskViewModel(Application application) {
        super(application);
        date = new MediatorLiveData<>();
        date.setValue(null);
        Log.d("aaaa", "TaskViewModel: getRepository");
        dataRepository = ((MyApplication) application).getRepository();

        listTask = Transformations.switchMap(date, input -> {
            Log.d("aaaaa", "TaskViewModel transformations: value = " + date.getValue());
            return dataRepository.getTasks(date.getValue());
        });
    }

    public LiveData<List<TaskEntity>> getListTask() {
        return listTask;
    }

    public void setDate(Date date) {
        this.date.setValue(date);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TaskViewModel(mApplication);
        }
    }

    public DataRepository getDataRepository() {
        return dataRepository;
    }
}