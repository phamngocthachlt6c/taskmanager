package com.ngocthach.taskmanager.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.adapter.RecyclerTaskListAdapter;
import com.ngocthach.taskmanager.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThachPham on 13/12/2017.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.listTask)
    RecyclerView taskRecyclerView;
    @BindView(R.id.noDataLayout)
    View noDataLayout;
    @BindView(R.id.loadingLayout)
    View loadingLayout;

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
        taskListAdapter = new RecyclerTaskListAdapter(getContext(), taskViewModel);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.setAdapter(taskListAdapter);
        taskListAdapter.setSwipeToDeleteItem(taskRecyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TaskViewModel.Factory factory = new TaskViewModel.Factory(getActivity().getApplication());
        taskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel.class);
        taskViewModel.setDate(Calendar.getInstance().getTime()); // change the date string param to be Date
        Log.d("aaaaaa", "onActivityCreated: currentDate = " + Calendar.getInstance().getTime().getYear());
        taskViewModel.getListTaskObserver().observe(this, (List<TaskEntity> tasks) -> {
            if(tasks!= null && !tasks.isEmpty()) {
                noDataLayout.setVisibility(View.GONE);
                taskRecyclerView.setVisibility(View.VISIBLE);
                taskListAdapter.loadListTask(tasks);
            } else {
                taskRecyclerView.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
            }
            loadingLayout.setVisibility(View.GONE);
        });

        Log.d("aaaaaa", "onActivityCreated: date______" + new Date(2017, 12, 11, 12, 12, 12).getYear());
    }

    public void changeListTask(Date date) {
        Log.d("bbbbbbb", "changeListTask: year = " + date.getYear() + ", tostring = " + date);
        loadingLayout.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.GONE);
        taskRecyclerView.setVisibility(View.GONE);
        taskViewModel.setDate(date);
    }

    public void insertTaskToList(TaskEntity taskEntity) {
        taskViewModel.insertList(taskEntity);
    }

}
