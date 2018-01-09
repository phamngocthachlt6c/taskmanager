package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.entity.AssetEntity;
import com.ngocthach.taskmanager.viewmodel.AssetViewModel;
import com.squareup.picasso.Picasso;

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
    private Context mContext;
    private AssetViewModel mViewModel;

    public AssetsListAdapter(Context context, AssetViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;
        assetEntities = new ArrayList<>();
    }

    public void loadList(List<AssetEntity> list) {
        assetEntities.clear();
        assetEntities.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public AssetVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_asset_item, parent, false);
        return new AssetVH(view);
    }

    @Override
    public void onBindViewHolder(AssetVH holder, int position) {
        holder.assetTitle.setText(assetEntities.get(position).getName());
        Picasso.with(mContext).load(assetEntities.get(position).getIconUrl())
                .error(R.mipmap.ic_launcher)
                .into(holder.assetIcon);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.getMenuInflater().inflate(R.menu.asset_list_popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                mViewModel.deleteAsset(assetEntities.get(position));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
        holder.point.setText(String.valueOf(assetEntities.get(position).getValue()));
        holder.plusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetEntity entity = assetEntities.get(position);
                entity.setValue(entity.getValue() + 1);
                mViewModel.updateAsset(entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assetEntities.size();
    }

    class AssetVH extends RecyclerView.ViewHolder {

        @BindView(R.id.assetTitle)
        TextView assetTitle;
        @BindView(R.id.iconView)
        ImageView assetIcon;
        @BindView(R.id.assetItemPlus)
        Button plusBt;
        @BindView(R.id.point)
        TextView point;

        public AssetVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
