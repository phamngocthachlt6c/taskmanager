package com.ngocthach.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.db.entity.PrincipleEntity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */

public class PrincipleViewModel extends AndroidViewModel {

    private LiveData<List<PrincipleEntity>> principleLiveList;
    private WeakReference<Application> mApplication;

    public PrincipleViewModel(@NonNull WeakReference<Application> application) {
        super(application.get());
        mApplication = application;
        principleLiveList = ((MyApplication) mApplication.get()).getRepository().getPrinciples();
    }

    public LiveData<List<PrincipleEntity>> getLiveList() {
        return principleLiveList;
    }

    public void deletePrinciple(PrincipleEntity principleEntity) {
        ((MyApplication) mApplication.get()).getMyAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) mApplication.get()).getRepository().deletePrinciple(principleEntity);
            }
        });
    }

    public void updatePrinciple(PrincipleEntity entity) {
        ((MyApplication) mApplication.get()).getMyAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) mApplication.get()).getRepository().updatePrinciple(entity);
            }
        });
    }
}
