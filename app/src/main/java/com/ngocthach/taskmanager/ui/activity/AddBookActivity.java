package com.ngocthach.taskmanager.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.db.entity.BookEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 10/01/2018.
 */

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.edBookName)
    EditText bookNameEditText;
    @BindView(R.id.edAuthor)
    EditText authorEditText;
    @BindView(R.id.addBookButton)
    Button addBook;
    @BindView(R.id.cancelButton)
    Button cancel;

    private String iconUrl = Constants.DEFAULT_ASSET_ICON_PATH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);

        addBook.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBookButton:
                BookEntity insertedBook = getInfo();
                new Thread(() -> ((MyApplication) getApplication()).getRepository()
                        .insertBook(insertedBook)).start();
                super.onBackPressed();
                break;

            case R.id.cancelButton:
                setResult(Constants.ADD_TASK_FAILED);
                super.onBackPressed();
                break;
        }
    }

    private BookEntity getInfo() {
        BookEntity book = new BookEntity();
        book.setBookName(bookNameEditText.getText().toString());
        book.setAuthor(authorEditText.getText().toString());
        return book;
    }
}
