package com.ngocthach.taskmanager.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ngocthach.taskmanager.db.entity.BookEntity;

import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 09/01/2018.
 */

@Dao
public interface BookDao {
    @Query("SELECT * FROM books")
    LiveData<List<BookEntity>> loadAllBooks();

    @Query("SELECT * FROM books WHERE id = :id")
    LiveData<BookEntity> loadBook(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBook(BookEntity bookEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListBook(List<BookEntity> books);

    @Update
    int updateBook(BookEntity book);

    @Delete
    void deleteBook(BookEntity book);
}
