package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngocthach.taskmanager.AppExecutors;
import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.AppDatabase;
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
    private Context context;

    public RecyclerTaskListAdapter(Context context, List<TaskEntity> listTask) {
        this.listTask = listTask;
        dateFormat = new SimpleDateFormat("HH:mm");
        this.context = context;
    }

    public void loadListTask(List<TaskEntity> list) {
        Log.d("aaaaaaa", "loadListTask: notifyDatasetChanged");
        if (listTask == null) {
            listTask = list;
        } else {
            listTask.clear();
            listTask.addAll(list);
        }

        // this method not refresh all the item, so don't care about list too long
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
        holder.isDoneCb.setOnCheckedChangeListener(null);
        holder.isDoneCb.setChecked(listTask.get(position).isDone());
        holder.isDoneCb.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            listTask.get(position).setDone(isChecked);
            new Thread(() -> DataRepository.getInstance(AppDatabase.getInstance(context, new AppExecutors()))
                    .updateTask(listTask.get(position))).start();
        });
    }

    @Override
    public int getItemCount() {
        return listTask == null ? 0 : listTask.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.taskTitle)
        TextView taskTitle;
        @BindView(R.id.taskContent)
        TextView taskContent;
        @BindView(R.id.taskTime)
        TextView taskTime;
        @BindView(R.id.isDoneCb)
        CheckBox isDoneCb;
        @BindView(R.id.taskIsNotify)
        ImageView taskIsNotify;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void printList() {
        Log.d("aaaaaaaa", "printList: start print list **********************");
        for (TaskEntity task : listTask) {
            Log.d("aaaaaaa", "printList: task isdone = " + task.isDone());
        }
    }
}
