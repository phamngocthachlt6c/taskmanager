package com.ngocthach.taskmanager.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.entity.AssetEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */

public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.AssetVH> {

    private List<AssetEntity> assetEntities;

    public AssetsListAdapter() {
        assetEntities = new ArrayList<>();
    }

    public void loadList(List<AssetEntity> list) {
        assetEntities.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public AssetVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_asset_item, parent, false);
        return new AssetVH(view);
    }

    @Override
    public void onBindViewHolder(AssetVH holder, int position) {
        holder.assetTitle.setText(assetEntities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return assetEntities.size();
    }

    static class AssetVH extends RecyclerView.ViewHolder {

        @BindView(R.id.assetTitle)
        TextView assetTitle;

        public AssetVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
