package com.ngocthach.taskmanager.di;

import android.app.Application;

import com.ngocthach.taskmanager.viewmodel.BookViewModel;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * ${CLASS}
 * Created by ThachPham on 09/01/2018.
 */
@Module
public class BookViewModelModule {

    private WeakReference<Application> application;

    public BookViewModelModule(WeakReference<Application> application) {
        this.application = application;
    }

    @Provides
    public BookViewModel provideBookViewModel() {
        return new BookViewModel(application);
    }
}
