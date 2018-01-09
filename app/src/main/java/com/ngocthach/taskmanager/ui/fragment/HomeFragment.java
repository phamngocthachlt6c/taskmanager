package com.ngocthach.taskmanager.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.MySharedPreferences;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.adapter.TaskListAdapter;
import com.ngocthach.taskmanager.viewmodel.AssetViewModel;
import com.ngocthach.taskmanager.viewmodel.PrincipleViewModel;
import com.ngocthach.taskmanager.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;
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
    @BindView(R.id.noDataLayout)
    View noDataLayout;
    @BindView(R.id.loadingLayout)
    View loadingLayout;

    @Inject
    MySharedPreferences sharedPreferences;
    @Inject
    AssetViewModel mAssetViewModel;
    @Inject
    PrincipleViewModel mPrincipleViewModel;
    private TaskViewModel taskViewModel;
    private TaskListAdapter taskListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ((MyApplication) getActivity().getApplication()).getMyComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TaskViewModel.Factory factory = new TaskViewModel.Factory(getActivity().getApplication());
        taskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel.class);
        taskViewModel.setDate(Calendar.getInstance().getTime());
        taskListAdapter = new TaskListAdapter(getContext(), taskViewModel, mAssetViewModel, mPrincipleViewModel, sharedPreferences);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.setAdapter(taskListAdapter);
        taskListAdapter.setSwipeToDeleteItem(taskRecyclerView);

        Log.d("aaaaaa", "onActivityCreated: currentDate = " + Calendar.getInstance().getTime().getYear());
        taskViewModel.getListTaskObserver().observe(this, (List<TaskEntity> tasks) -> {
            if(tasks!= null) {
                noDataLayout.setVisibility(View.GONE);
                taskRecyclerView.setVisibility(View.VISIBLE);
                taskListAdapter.loadListTask(tasks);
            } else {
                taskRecyclerView.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
            }
            loadingLayout.setVisibility(View.GONE);
        });
    }

    public void changeListTask(Date date) {
        Log.d("bbbbbbb", "changeListTask: year = " + date.getYear() + ", tostring = " + date);
        taskListAdapter.setHeaderDate(date);
        loadingLayout.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.GONE);
        taskRecyclerView.setVisibility(View.GONE);
        taskViewModel.setDate(date);
    }

    public void insertTaskToList(TaskEntity taskEntity) {
        taskViewModel.insertList(taskEntity);
    }

    public void refreshList() {
        taskViewModel.refreshList();
    }

}
