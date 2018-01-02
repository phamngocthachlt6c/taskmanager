package com.ngocthach.taskmanager.ui.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.adapter.PriorityAdapter;
import com.ngocthach.taskmanager.ui.fragment.ChooseImageFragment;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThachPham on 19/12/2017.
 */

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.editTextTaskName)
    EditText taskNameEditText;
    @BindView(R.id.editTextTaskContent)
    MultiAutoCompleteTextView taskContentEd;
    @BindView(R.id.dayOnWeek)
    Spinner daySpinner;
    @BindView(R.id.priority)
    Spinner prioritySpinner;
    @BindView(R.id.datePicker)
    DatePicker datePicker;
    @BindView(R.id.timePicker)
    TimePicker timePicker;
    @BindView(R.id.addTaskButton)
    Button addTask;
    @BindView(R.id.cancelButton)
    Button cancel;
    @BindView(R.id.radioGroupTypeOfTask)
    RadioGroup typeOfTaskRadioGroup;
    @BindView(R.id.panelDailyTask)
    View panelDailyTask;
    @BindView(R.id.iconView)
    ImageView iconView;

    private int priorityItemSelected;
    private int mStackLevel;
    private String iconUrl = Constants.DEFAULT_TASK_ICON_PATH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        Picasso.with(this).load(Constants.DEFAULT_TASK_ICON_PATH).error(R.mipmap.ic_launcher).into(iconView);
        panelDailyTask = findViewById(R.id.panelDailyTask);
        typeOfTaskRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioDailyTask:
                    datePicker.setVisibility(View.GONE);
                    panelDailyTask.setVisibility(View.VISIBLE);
                    break;
                case R.id.radioSingleDayTask:
                    panelDailyTask.setVisibility(View.GONE);
                    datePicker.setVisibility(View.VISIBLE);
                    break;
            }
        });

        PriorityAdapter priorityAdapter = new PriorityAdapter(this, getResources().getStringArray(R.array.priority));
        prioritySpinner.setAdapter(priorityAdapter);
        prioritySpinner.setSelection(1);
        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priorityItemSelected = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        priorityItemSelected = 1;

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.dayOnWeek, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        daySpinner.setAdapter(adapter);
//        daySpinner.setOnItemSelectedListener(this);
//        spinnerItemSelected = 0;

        addTask.setOnClickListener(this);
        cancel.setOnClickListener(this);
        iconView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTaskButton:
                TaskEntity insertedTask = getInfo();
                new Thread(() -> {
                    long success = ((MyApplication) getApplication()).getRepository()
                            .insertTask(insertedTask);
                    Log.d("aaaaa", "onClick: success = " + success);
                    if(success > 0) {
                        runOnUiThread(() -> {
                            Intent data = new Intent();
                            data.putExtra("taskEntity", insertedTask);
                            setResult(Constants.ADD_TASK_SUCCESS, data);
                            super.onBackPressed();
                        });
                    }
                }).start();
                break;

            case R.id.cancelButton:
                setResult(Constants.ADD_TASK_FAILED);
                super.onBackPressed();
                break;

            case R.id.iconView:
                ChooseImageFragment fragment = ChooseImageFragment.newInstance();
                fragment.setAssetType(ChooseImageFragment.Assets.TASK);
                fragment.setOnChangeIconImage(path -> {
                    iconUrl = path;
                    Picasso.with(AddTaskActivity.this).load(iconUrl)
                            .error(R.mipmap.ic_launcher)
                            .into(iconView);
                });
                showDialog(fragment);
                break;
        }
    }

    private TaskEntity getInfo() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setIconUrl(iconUrl);
        int hour, minute;
        if (Build.VERSION.SDK_INT < 23) {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        } else {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }
        if (typeOfTaskRadioGroup.getCheckedRadioButtonId() == R.id.radioDailyTask) {
            taskEntity.setTypeOfTask(Constants.TaskEntity.DAILY_TASK);
        } else {
            taskEntity.setTypeOfTask(Constants.TaskEntity.SINGLE_DAY_TASK);
        }
        taskEntity.setPriority(priorityItemSelected);
        Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth(),
                hour, minute);
        Log.d("aaaaa", "onClick: Fragment addtask date = " + date);
        taskEntity.setDate(date);
        taskEntity.setTitle(taskNameEditText.getText().toString());
        taskEntity.setContent(taskContentEd.getText().toString());
        return taskEntity;
    }

    void showDialog(DialogFragment dialogFragment) {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        dialogFragment.show(ft, "dialog");
    }
}
