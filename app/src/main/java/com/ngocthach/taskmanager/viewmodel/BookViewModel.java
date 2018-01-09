package com.ngocthach.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.db.entity.BookEntity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 09/01/2018.
 */

public class BookViewModel extends AndroidViewModel {

    private LiveData<List<BookEntity>> listLiveBooks;
    private WeakReference<Application> mApplication;

    public BookViewModel(@NonNull WeakReference<Application> application) {
        super(application.get());
        mApplication = application;
        listLiveBooks = ((MyApplication) application.get()).getRepository().getBooks();
    }

    public LiveData<List<BookEntity>> getLiveBook() {
        return listLiveBooks;
    }

    public void deleteBook(BookEntity bookEntity) {
        ((MyApplication) mApplication.get()).getMyAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) mApplication.get()).getRepository().deleteBook(bookEntity);
            }
        });
    }

    public void updateBook(BookEntity bookEntity) {
        ((MyApplication) mApplication.get()).getMyAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ((MyApplication) mApplication.get()).getRepository().updateBook(bookEntity);
            }
        });
    }
}