package com.ngocthach.taskmanager.ui.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngocthach.taskmanager.AppExecutors;
import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.adapter.RecyclerTaskListAdapter;
import com.ngocthach.taskmanager.viewmodel.TaskViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThachPham on 13/12/2017.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.listTask)
    RecyclerView taskRecyclerView;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TaskViewModel taskViewModel;
    private RecyclerTaskListAdapter taskListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        taskListAdapter = new RecyclerTaskListAdapter(null);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.setAdapter(taskListAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel.class);
        taskViewModel = new TaskViewModel(DataRepository.getInstance(((MyApplication) getContext().getApplicationContext()).getDatabase()));
        taskViewModel.setDate("12-12-2017"); // change the date string param to be Date
        taskViewModel.getListTask().observe(this, (List<TaskEntity> tasks) -> {
            taskListAdapter.loadListTask(tasks);
        });
    }
}
