package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngocthach.taskmanager.AppExecutors;
import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.activity.MainActivity;
import com.ngocthach.taskmanager.ui.activity.TaskDetailActivity;
import com.ngocthach.taskmanager.viewmodel.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThachPham on 13/12/2017.
 */

public class RecyclerTaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TASK_VIEW = 0;
    private static final int FOOTER_VIEW = 1;
    private static final int HEADER_VIEW = 1;

    private List<TaskEntity> listTask;
    private SimpleDateFormat dateFormat;
    private Context context;
    private TaskViewModel viewModel;
    private Bitmap iconDeleted;

    private int sortType = Constants.TIMING;

    private Rect rs;

    public RecyclerTaskListAdapter(Context context/*, executors */, TaskViewModel taskViewModel) {
        listTask = new ArrayList<>();
        dateFormat = new SimpleDateFormat("HH:mm");
        this.context = context;
        viewModel = taskViewModel;
        iconDeleted = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_delete);
        rs = new Rect();
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
                if (viewHolder instanceof TaskViewHolder) {
                    TaskEntity task = listTask.get(viewHolder.getAdapterPosition());
                    listTask.remove(viewHolder.getAdapterPosition());
                    viewModel.deleteTask(viewHolder.getAdapterPosition());
                    notifyDataChanged();
                    new Thread(() -> DataRepository.getInstance(AppDatabase.getInstance(context, new AppExecutors()))
                            .deleteTask(task)).start();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Get RecyclerView item from the ViewHolder
                View itemView = viewHolder.itemView;
                if (viewHolder instanceof TaskViewHolder) {
                    Paint p = new Paint();
                    p.setColor(context.getResources().getColor(R.color.light_red));
                    p.setAlpha((int) (255 * (dX / (itemView.getRight() - itemView.getLeft()))));
                    c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                            (float) itemView.getBottom(), p);
                    rs.left = itemView.getLeft() + 10;
                    rs.top = itemView.getTop() + (itemView.getBottom() - itemView.getTop() - 60) / 2;
                    rs.right = rs.left + 60;
                    rs.bottom = rs.top + 60;
                    c.drawBitmap(iconDeleted, null, rs, p);

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, itemView.getLeft(), dY, actionState, isCurrentlyActive);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void setSortType(int type) {
        sortType = type;
    }

    public void loadListTask(List<TaskEntity> list) {
        Log.d("aaaaaaa", "loadListTask: notifyDatasetChanged");
        if (list == null || listTask == null) {
            return;
        }
        listTask.clear();
        listTask.addAll(list);

        // this method not refresh all the item, so don't care about list too long
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        for(TaskEntity taskEntity : listTask) {
            taskEntity.setSortType(sortType);
        }
        sortList();
        notifyDataSetChanged();
    }

    private void sortList() {
        Collections.sort(listTask);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 1) {
            return HEADER_VIEW;
        } else if (position < listTask.size() + 1) {
            return TASK_VIEW;
        } else {
            return FOOTER_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_footer_item, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == TASK_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task_item, parent, false);
            return new TaskViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_footer_item, parent, false);
            return new FooterViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            // Check list is null then set visible gone
            // If the date not TO DAY...
        } else if (holder instanceof TaskViewHolder) {
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
            taskViewHolder.taskTitle.setText(listTask.get(position - 1).getTitle());
            taskViewHolder.taskContent.setText(listTask.get(position - 1).getContent());
            taskViewHolder.taskTime.setText(dateFormat.format(listTask.get(position - 1).getDate()));
            taskViewHolder.isDoneCb.setOnCheckedChangeListener(null);
            taskViewHolder.isDoneCb.setChecked(listTask.get(position - 1).isDone());
            taskViewHolder.isDoneCb.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                listTask.get(position - 1).setDone(isChecked);
                new Thread(() -> DataRepository.getInstance(AppDatabase.getInstance(context, new AppExecutors()))
                        .updateTask(listTask.get(position - 1))).start();
            });
            if (listTask.get(position - 1).getPriority() == Constants.TaskEntity.HIGHT_PRIOR) {
                taskViewHolder.bgLayout.setBackgroundColor(context.getResources().getColor(R.color.high_priority));
            } else if (listTask.get(position - 1).getPriority() == Constants.TaskEntity.MEDIUM_PRIOR) {
                taskViewHolder.bgLayout.setBackgroundColor(context.getResources().getColor(R.color.medium_priority));
            } else {
                taskViewHolder.bgLayout.setBackgroundColor(context.getResources().getColor(R.color.low_priority));
            }

            taskViewHolder.itemView.setOnTouchListener((view, motionEvent) -> {
                view.performClick();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        taskViewHolder.foregroundLayout.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(context, TaskDetailActivity.class);
                        intent.putExtra("taskEntity", listTask.get(position - 1));
                        ((MainActivity) context).startActivityForResult(intent, Constants.EDIT_TASK_REQUEST);
                    case MotionEvent.ACTION_CANCEL:
                        taskViewHolder.foregroundLayout.setVisibility(View.GONE);
                        break;
                }

                return true;
            });
        } else {
            // Check list is null then set visible gone
            // If the date not TO DAY...
        }
    }

    @Override
    public int getItemCount() {
        return listTask == null ? 2 : listTask.size() + 2;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bgLayout)
        View bgLayout;
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
        @BindView(R.id.foregroundLayout)
        View foregroundLayout;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // may be appear Ads here
    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void printList() {
        Log.d("aaaaaaaa", "printList: start print list **********************");
        for (TaskEntity task : listTask) {
            Log.d("aaaaaaa", "printList: task isdone = " + task.isDone());
        }
    }
}
