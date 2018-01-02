package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.entity.PrincipleEntity;
import com.ngocthach.taskmanager.viewmodel.PrincipleViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */

public class PrincipleListAdapter extends RecyclerView.Adapter<PrincipleListAdapter.PrincipleVH> {

    private List<PrincipleEntity> principleEntities;
    private Context mContext;
    private PrincipleViewModel mViewModel;

    public PrincipleListAdapter(Context context, PrincipleViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;
        principleEntities = new ArrayList<>();
    }

    public void loadList(List<PrincipleEntity> list) {
        principleEntities.clear();
        principleEntities.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public PrincipleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_principle_item, parent, false);
        return new PrincipleVH(view);
    }

    @Override
    public void onBindViewHolder(PrincipleVH holder, int position) {
        holder.principleTitle.setText(principleEntities.get(position).getTitle());
        Picasso.with(mContext).load(principleEntities.get(position).getIconUrl())
                .error(R.mipmap.ic_launcher)
                .into(holder.principleIcon);
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
                                mViewModel.deletePrinciple(principleEntities.get(position));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return principleEntities.size();
    }

    class PrincipleVH extends RecyclerView.ViewHolder {

        @BindView(R.id.principleTitle)
        TextView principleTitle;
        @BindView(R.id.iconView)
        ImageView principleIcon;

        public PrincipleVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

