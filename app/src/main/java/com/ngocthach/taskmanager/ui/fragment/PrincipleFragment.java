package com.ngocthach.taskmanager.ui.fragment;

import android.arch.lifecycle.Observer;
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
import com.ngocthach.taskmanager.db.entity.PrincipleEntity;
import com.ngocthach.taskmanager.ui.adapter.PrincipleListAdapter;
import com.ngocthach.taskmanager.viewmodel.PrincipleViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */

public class PrincipleFragment extends Fragment {

    @BindView(R.id.listPrinciple)
    RecyclerView principleRecyclerView;
    PrincipleListAdapter mPrincipleListAdapter;

    @Inject
    MySharedPreferences sharedPreferences;
    @Inject
    PrincipleViewModel principleViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_principle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ((MyApplication) getActivity().getApplication()).getMyComponent().inject(this);
        mPrincipleListAdapter = new PrincipleListAdapter(getContext(), principleViewModel);
        principleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        principleRecyclerView.setAdapter(mPrincipleListAdapter);
        principleViewModel.getLiveList().observe(this, new Observer<List<PrincipleEntity>>() {
            @Override
            public void onChanged(@Nullable List<PrincipleEntity> principleEntities) {
                if (principleEntities != null) {
                    mPrincipleListAdapter.loadList(principleEntities);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
