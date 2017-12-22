package com.ngocthach.taskmanager.ui.activity;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.fragment.CalendarFragment;
import com.ngocthach.taskmanager.ui.fragment.EditTaskFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 22/12/2017.
 */

public class TaskDetailActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.priority)
    TextView priority;
    @BindView(R.id.time)
    TextView time;

    private TaskEntity taskEntity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        taskEntity = getIntent().getParcelableExtra("taskEntity");
        if(taskEntity != null) {
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null) {
                actionBar.setTitle(taskEntity.getTitle());
            }

            title.setText(taskEntity.getTitle());
            content.setText(taskEntity.getContent());
            if(taskEntity.getPriority() == Constants.TaskEntity.HIGHT_PRIOR) {
                priority.setText(getResources().getStringArray(R.array.priority)[Constants.TaskEntity.MEDIUM_PRIOR]);
                priority.setBackgroundColor(getResources().getColor(R.color.high_priority));
            } else if(taskEntity.getPriority() == Constants.TaskEntity.MEDIUM_PRIOR) {
                priority.setText(getResources().getStringArray(R.array.priority)[Constants.TaskEntity.MEDIUM_PRIOR]);
                priority.setBackgroundColor(getResources().getColor(R.color.medium_priority));
            } else {
                priority.setText(getResources().getStringArray(R.array.priority)[Constants.TaskEntity.LOW_PRIOR]);
                priority.setBackgroundColor(getResources().getColor(R.color.low_priority));
            }
            time.setText(taskEntity.getDate().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu_task_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new EditTaskFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public TaskEntity getTaskEntity() {
        return taskEntity;
    }
}
