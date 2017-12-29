package com.ngocthach.taskmanager.ui.fragment;

import android.arch.lifecycle.Observer;
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
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.common.MySharedPreferences;
import com.ngocthach.taskmanager.db.entity.AssetEntity;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.adapter.RecyclerTaskListAdapter;
import com.ngocthach.taskmanager.viewmodel.AssetViewModel;
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

public class AssetsFragment extends Fragment {

    @Inject
    MySharedPreferences sharedPreferences;
    @Inject
    AssetViewModel assetViewModel;

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
        assetViewModel.getLiveAssets().observe(this, new Observer<List<AssetEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssetEntity> assetEntities) {
                if(assetEntities != null) {
                    for(AssetEntity a : assetEntities) {
                        Log.d("aaaaaaa", "onChanged: " + a.getName());
                    }
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
