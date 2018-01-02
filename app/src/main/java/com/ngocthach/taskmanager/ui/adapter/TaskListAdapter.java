package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.common.MySharedPreferences;
import com.ngocthach.taskmanager.common.TimeUtils;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.activity.MainActivity;
import com.ngocthach.taskmanager.ui.activity.TaskDetailActivity;
import com.ngocthach.taskmanager.viewmodel.TaskViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThachPham on 13/12/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TASK_VIEW = 0;
    private static final int FOOTER_VIEW = 1;
    private static final int HEADER_VIEW = 2;

    private List<TaskEntity> listTask;
    private SimpleDateFormat dateFormat;
    private Context context;
    private TaskViewModel viewModel;
    private Bitmap iconDeleted;

    private MySharedPreferences sharedPreferences;
    private int sortType;
    private Rect rs;
    private CountDownTimer countDownTimer;

    public TaskListAdapter(Context context/*, executors */, TaskViewModel taskViewModel, MySharedPreferences sharedPreferences) {
        listTask = new ArrayList<>();
        dateFormat = new SimpleDateFormat("HH:mm");
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        viewModel = taskViewModel;
        iconDeleted = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_delete);
        rs = new Rect();

        setSortType(sharedPreferences.getSortType());
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
                    TaskEntity task = listTask.get(viewHolder.getAdapterPosition() - 1);
                    listTask.remove(viewHolder.getAdapterPosition() - 1);
                    viewModel.deleteTask(viewHolder.getAdapterPosition() - 1);
                    notifyDataChanged();
                    new Thread(() -> ((MyApplication) context.getApplicationContext()).getRepository()
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
        Log.d("aaaaaaa", "loadListTask: notifyDatasetChanged " + sortType);

        if (list == null || listTask == null) {
            return;
        }
        listTask.clear();
        listTask.addAll(list);

        // this method not refresh all the item, so don't care about list too long
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        for (TaskEntity taskEntity : listTask) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_header_item, parent, false);
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

            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (headerViewHolder.settingLayout.getVisibility() == View.VISIBLE) {
                        Animation animHide = AnimationUtils.loadAnimation(context, R.anim.view_hide);
                        headerViewHolder.settingLayout.startAnimation(animHide);
                        headerViewHolder.settingLayout.setVisibility(View.GONE);
                        headerViewHolder.iconSettingBt.setImageResource(R.mipmap.arrow_down);
                    } else {
                        headerViewHolder.settingLayout.setVisibility(View.VISIBLE);
                        headerViewHolder.iconSettingBt.setImageResource(R.mipmap.arrow_up);
                        Animation animShow = AnimationUtils.loadAnimation(context, R.anim.view_show);
                        headerViewHolder.settingLayout.startAnimation(animShow);
                    }
                }
            };
            headerViewHolder.headerSettingBt.setOnClickListener(onClickListener);
            headerViewHolder.headerSettingBt.setOnClickListener(onClickListener);
            headerViewHolder.iconSettingBt.setOnClickListener(onClickListener);

            if (sharedPreferences.getSortType() == Constants.TIMING) {
                headerViewHolder.radioSortTypeTime.setChecked(true);
            } else {
                headerViewHolder.radioSorttypePriority.setChecked(true);
            }
            headerViewHolder.radioGroup.setOnCheckedChangeListener((radioGroup, id) -> {
                switch (id) {
                    case R.id.radioSortTypeTime:
                        sharedPreferences.setSortType(Constants.TIMING);
                        setSortType(Constants.TIMING);
                        notifyDataChanged();
                        break;
                    case R.id.radioSortTypePriority:
                        sharedPreferences.setSortType(Constants.PRIORITY);
                        setSortType(Constants.PRIORITY);
                        notifyDataChanged();
                        break;
                }
            });

            if(TimeUtils.isSameDay(new Date(), viewModel.getValue())) {
                headerViewHolder.cardViewTime.setVisibility(View.VISIBLE);
                if(countDownTimer != null) {
                    countDownTimer.cancel();
                }
                countDownTimer = new CountDownTimer(TimeUtils.getRemainTimeToDay(), 500) {
                    boolean isShowColon;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        long hour = millisUntilFinished / (60 * 60 * 1000);
                        long min = (millisUntilFinished % (60 * 60 * 1000)) / (60 * 1000);
                        headerViewHolder.remainTime
                                .setText(isShowColon ? (String.format("%02d:%02d", hour, min + 1)) : String.format("%02d %02d", hour, min + 1));
                        isShowColon = !isShowColon;
                    }

                    @Override
                    public void onFinish() {
                        // TODO: report day
                        headerViewHolder.remainTime.setText("00:00");
                        cancel();
                    }
                };
                countDownTimer.start();
            } else {
                if(countDownTimer != null) {
                    countDownTimer.cancel();
                }
                headerViewHolder.cardViewTime.setVisibility(View.GONE);
            }

        } else if (holder instanceof TaskViewHolder) {
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
            Picasso.with(context).load(listTask.get(position - 1).getIconUrl())
                    .error(R.mipmap.ic_launcher)
                    .into(taskViewHolder.iconView);
            taskViewHolder.taskTitle.setText(listTask.get(position - 1).getTitle());
            taskViewHolder.taskContent.setText(listTask.get(position - 1).getContent());
            taskViewHolder.taskTime.setText(dateFormat.format(listTask.get(position - 1).getDate()));
            taskViewHolder.isDoneCb.setOnCheckedChangeListener(null);
            taskViewHolder.isDoneCb.setChecked(listTask.get(position - 1).isDone());
            taskViewHolder.isDoneCb.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                listTask.get(position - 1).setDone(isChecked);
                new Thread(() -> ((MyApplication) context.getApplicationContext()).getRepository()
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
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.text.setText("afsdf");
        }
    }

    @Override
    public int getItemCount() {
        // Plus more 2 because it always have 2 item header and footer
        return listTask == null ? 2 : (listTask.size() + 2);
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
        @BindView(R.id.iconView)
        ImageView iconView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // may be appear Ads here
    class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.zzzz)
        TextView text;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.settingLayout)
        View settingLayout;
        @BindView(R.id.headerSettingBt)
        View headerSettingBt;
        @BindView(R.id.iconSettingBt)
        ImageView iconSettingBt;
        @BindView(R.id.radioSortType)
        RadioGroup radioGroup;
        @BindView(R.id.radioSortTypeTime)
        RadioButton radioSortTypeTime;
        @BindView(R.id.radioSortTypePriority)
        RadioButton radioSorttypePriority;
        @BindView(R.id.remainTime)
        TextView remainTime;
        @BindView(R.id.remainTimeCardView)
        View cardViewTime;

        public HeaderViewHolder(View itemView) {
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
