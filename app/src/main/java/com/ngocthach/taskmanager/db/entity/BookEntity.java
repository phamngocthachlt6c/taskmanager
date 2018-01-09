package com.ngocthach.taskmanager.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * ${CLASS}
 * Created by ThachPham on 09/01/2018.
 */
@Entity(tableName = "books")
public class BookEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String bookName;
    private String author;
    private boolean isDone;

    public BookEntity() {
    }

    public BookEntity(String bookName, String author, boolean isDone) {
        this.bookName = bookName;
        this.author = author;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
