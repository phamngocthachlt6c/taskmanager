package com.ngocthach.taskmanager.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ngocthach.taskmanager.AppExecutors;
import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThachPham on 19/12/2017.
 */

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.editTextTaskName)
    EditText taskNameEditText;
    @BindView(R.id.editTextTaskContent)
    MultiAutoCompleteTextView taskContentEd;
    @BindView(R.id.dayOnWeek)
    Spinner daySpinner;
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

    private int spinnerItemSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dayOnWeek, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        daySpinner.setOnItemSelectedListener(this);
        spinnerItemSelected = 0;

        addTask.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTaskButton:
                addTask();
                setResult(Constants.ADD_TASK_SUCCESS);
                super.onBackPressed();
                break;

            case R.id.cancelButton:
                setResult(Constants.ADD_TASK_FAILED);
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        spinnerItemSelected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void addTask() {
        TaskEntity taskEntity = new TaskEntity();
        int hour, minute;
        if (Build.VERSION.SDK_INT < 23) {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        } else {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }
        if(typeOfTaskRadioGroup.getCheckedRadioButtonId() == R.id.radioDailyTask) {
            taskEntity.setTypeOfTask(Constants.TaskEntity.DAILY_TASK);
        } else {
            taskEntity.setTypeOfTask(Constants.TaskEntity.SINGLE_DAY_TASK);
        }
        Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth(),
                hour, minute);
        Log.d("aaaaa", "onClick: Fragment addtask date = " + date);
        taskEntity.setDate(date);
        taskEntity.setDayInWeek(spinnerItemSelected);
        taskEntity.setTitle(taskNameEditText.getText().toString());
        taskEntity.setContent(taskContentEd.getText().toString());
        new Thread(() -> DataRepository.getInstance(AppDatabase.getInstance(this, new AppExecutors()))
                .insertTask(taskEntity)).start();
    }
}
