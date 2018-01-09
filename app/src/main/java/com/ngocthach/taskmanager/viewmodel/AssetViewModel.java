package com.ngocthach.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.db.entity.AssetEntity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 29/12/2017.
 */

public class AssetViewModel extends AndroidViewModel {

    private LiveData<List<AssetEntity>> listLiveAssets;
    private WeakReference<Application> mApplication;

    public AssetViewModel(@NonNull WeakReference<Application> application) {
        super(application.get());
        mApplication = application;
        listLiveAssets = ((MyApplication) application.get()).getRepository().getAssets();
    }

    public LiveData<List<AssetEntity>> getLiveAssets() {
        return listLiveAssets;
    }

    public void deleteAsset(AssetEntity assetEntity) {
        ((MyApplication) mApplication.get()).getMyAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) mApplication.get()).getRepository().deleteAsset(assetEntity);
            }
        });
    }

    public void updateAsset(AssetEntity assetEntity) {
        ((MyApplication) mApplication.get()).getMyAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) mApplication.get()).getRepository().updateAsset(assetEntity);
            }
        });
    }
}
