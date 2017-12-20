package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import java.util.ArrayList;
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
    private Bitmap iconDeleted;

    public RecyclerTaskListAdapter(Context context/*, executors */) {
        listTask = new ArrayList<>();
        dateFormat = new SimpleDateFormat("HH:mm");
        this.context = context;
         iconDeleted = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }

    public void setSwipeToDeleteItem(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                TaskEntity task = listTask.get(viewHolder.getAdapterPosition());
                listTask.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
                new Thread(() -> DataRepository.getInstance(AppDatabase.getInstance(context, new AppExecutors()))
                        .deleteTask(task)).start();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Get RecyclerView item from the ViewHolder
                View itemView = viewHolder.itemView;
                Paint p = new Paint();
                p.setColor(context.getResources().getColor(R.color.light_red));

                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                        (float) itemView.getBottom(), p);
                c.drawBitmap(iconDeleted, itemView.getLeft(), (float) itemView.getTop(), p);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void loadListTask(List<TaskEntity> list) {
        Log.d("aaaaaaa", "loadListTask: notifyDatasetChanged");
        if(list == null || listTask == null) {
            return;
        }
        listTask.clear();
        listTask.addAll(list);

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
