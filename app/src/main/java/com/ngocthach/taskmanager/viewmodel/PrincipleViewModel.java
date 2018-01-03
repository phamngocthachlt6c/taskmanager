package com.ngocthach.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.db.entity.PrincipleEntity;

import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */

public class PrincipleViewModel extends AndroidViewModel {

    private LiveData<List<PrincipleEntity>> principleLiveList;
    private Application mApplication;

    public PrincipleViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        principleLiveList = ((MyApplication) application).getRepository().getPrinciples();
    }

    public LiveData<List<PrincipleEntity>> getLiveList() {
        return principleLiveList;
    }

    public void deletePrinciple(PrincipleEntity principleEntity) {
        ((MyApplication) mApplication).getMyAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) mApplication).getRepository().deletePrinciple(principleEntity);
            }
        });
    }
}
