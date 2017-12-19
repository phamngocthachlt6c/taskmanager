package com.ngocthach.taskmanager.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThachPham on 13/12/2017.
 */

public class RecyclerTaskListAdapter extends RecyclerView.Adapter<RecyclerTaskListAdapter.TaskViewHolder> {

    private List<TaskEntity> listTask;
    private SimpleDateFormat dateFormat;

    public RecyclerTaskListAdapter(List<TaskEntity> listTask) {
        this.listTask = listTask;
        dateFormat = new SimpleDateFormat("HH:mm");
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
        holder.taskContent.setText(listTask.get(position).getContent());
        holder.taskTime.setText(dateFormat.format(listTask.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return listTask == null? 0 : listTask.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.taskTitle)
        TextView taskTitle;
        @BindView(R.id.taskContent)
        TextView taskContent;
        @BindView(R.id.taskTime)
        TextView taskTime;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
