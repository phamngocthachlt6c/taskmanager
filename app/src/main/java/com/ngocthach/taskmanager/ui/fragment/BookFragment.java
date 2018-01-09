package com.ngocthach.taskmanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.MySharedPreferences;
import com.ngocthach.taskmanager.ui.adapter.BooksListAdapter;
import com.ngocthach.taskmanager.viewmodel.BookViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 09/01/2018.
 */

public class BookFragment extends Fragment {

    @BindView(R.id.listBook)
    RecyclerView booksRecyclerView;
    BooksListAdapter mBooksListAdapter;

    @Inject
    MySharedPreferences sharedPreferences;
    @Inject
    BookViewModel bookViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ((MyApplication) getActivity().getApplication()).getMyComponent().inject(this);
        mBooksListAdapter = new BooksListAdapter(getContext(), bookViewModel);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        booksRecyclerView.setAdapter(mBooksListAdapter);
        bookViewModel.getLiveBook().observe(this, bookEntities -> {
            if (bookEntities != null) {
                mBooksListAdapter.loadList(bookEntities);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}