package com.ngocthach.taskmanager.di;

import android.app.Application;

import com.ngocthach.taskmanager.viewmodel.AssetViewModel;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * ${CLASS}
 * Created by ThachPham on 29/12/2017.
 */

@Module
public class AssetViewModelModule {

    private WeakReference<Application> application;

    public AssetViewModelModule(WeakReference<Application> application) {
        this.application = application;
    }

    @Provides
    public AssetViewModel provideAssetViewModel() {
        return new AssetViewModel(application);
    }
}
