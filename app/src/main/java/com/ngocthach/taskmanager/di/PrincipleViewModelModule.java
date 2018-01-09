package com.ngocthach.taskmanager.di;

import android.app.Application;

import com.ngocthach.taskmanager.viewmodel.PrincipleViewModel;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */

@Module
public class PrincipleViewModelModule {

    private WeakReference<Application> application;

    public PrincipleViewModelModule(WeakReference<Application> application) {
        this.application = application;
    }

    @Provides
    PrincipleViewModel providePrincipleViewModel() {
        return new PrincipleViewModel(application);
    }
}
