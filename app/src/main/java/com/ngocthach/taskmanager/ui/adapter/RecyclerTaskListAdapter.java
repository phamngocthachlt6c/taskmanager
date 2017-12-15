package com.ngocthach.taskmanager.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThachPham on 13/12/2017.
 */

public class RecyclerTaskListAdapter extends RecyclerView.Adapter<RecyclerTaskListAdapter.TaskViewHolder> {

    private List<TaskEntity> listTask;

    public RecyclerTaskListAdapter(List<TaskEntity> listTask) {
        this.listTask = listTask;
    }

    public void loadListTask(List<TaskEntity> list) {
        if(listTask == null) {
            listTask = list;
        } else {
            listTask.clear();
            listTask.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.taskTitle.setText(listTask.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return listTask == null? 0 : listTask.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.taskTitle)
        TextView taskTitle;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
