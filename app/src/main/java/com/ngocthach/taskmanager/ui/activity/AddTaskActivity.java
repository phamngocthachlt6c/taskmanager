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
import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.Date;

/**
 * Created by ThachPham on 19/12/2017.
 */

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText taskNameEditText;
    private MultiAutoCompleteTextView taskContentEd;
    private Spinner daySpinner;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button addTask, cancel;
    private RadioGroup typeOfTaskRadioGroup;
    private View panelDailyTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_add_task);
        taskNameEditText = (EditText) findViewById(R.id.editTextTaskName);
        taskContentEd = (MultiAutoCompleteTextView) findViewById(R.id.editTextTaskContent);

        panelDailyTask = findViewById(R.id.panelDailyTask);
        typeOfTaskRadioGroup = (RadioGroup) findViewById(R.id.radioGroupTypeOfTask);
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

        daySpinner = (Spinner) findViewById(R.id.dayOnWeek);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dayOnWeek, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        daySpinner.setOnItemSelectedListener(this);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        addTask = (Button) findViewById(R.id.addTaskButton);
        addTask.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTaskButton:
                TaskEntity taskEntity = new TaskEntity();
                int hour, minute;
                if (Build.VERSION.SDK_INT < 23) {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                } else {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }

                Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth(),
                        hour, minute);
                Log.d("aaaaa", "onClick: Fragment addtask date = " + date);
                taskEntity.setDate(date);
                taskEntity.setTitle(taskNameEditText.getText().toString());
                taskEntity.setContent(taskContentEd.getText().toString());
                new Thread(() -> DataRepository.getInstance(AppDatabase.getInstance(this, new AppExecutors()))
                        .insertTask(taskEntity)).start();
                super.onBackPressed();
                break;

            case R.id.cancelButton:
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
