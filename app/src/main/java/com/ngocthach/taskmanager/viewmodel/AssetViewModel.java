package com.ngocthach.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.db.entity.AssetEntity;

import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 29/12/2017.
 */

public class AssetViewModel {

    private LiveData<List<AssetEntity>> listLiveAssets;
    private Application mApplication;

    public AssetViewModel(Application application) {
        mApplication = application;
        listLiveAssets = ((MyApplication) application).getRepository().getAssets();
    }

    public LiveData<List<AssetEntity>> getLiveAssets() {
        return listLiveAssets;
    }

    public void deleteAsset(AssetEntity assetEntity) {
        ((MyApplication) mApplication).getMyAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) mApplication).getRepository().deleteAsset(assetEntity);
            }
        });
    }
}
