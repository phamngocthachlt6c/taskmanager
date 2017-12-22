package com.ngocthach.taskmanager.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ngocthach.taskmanager.AppExecutors;
import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.activity.TaskDetailActivity;
import com.ngocthach.taskmanager.ui.adapter.PriorityAdapter;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 22/12/2017.
 */

public class EditTaskFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.editTextTaskName)
    EditText taskNameEditText;
    @BindView(R.id.editTextTaskContent)
    MultiAutoCompleteTextView taskContentEd;
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
    @BindView(R.id.my_toolbar)
    View toolbar;

    private TaskEntity taskEntity = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_task, container, false);
        ButterKnife.bind(this, view);
        addTask.setOnClickListener(this);
        taskEntity = ((TaskDetailActivity) getActivity()).getTaskEntity();
        toolbar.setVisibility(View.GONE);
        addTask.setText(getResources().getString(R.string.text_edit_task));
        if (taskEntity != null) {
            taskNameEditText.setText(taskEntity.getTitle());
            taskContentEd.setText(taskEntity.getContent());
            PriorityAdapter priorityAdapter = new PriorityAdapter(getActivity(), getResources().getStringArray(R.array.priority));
            prioritySpinner.setAdapter(priorityAdapter);
            prioritySpinner.setSelection(taskEntity.getPriority());
            prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    taskEntity.setPriority(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTaskButton:
                getInfo();
                new Thread(() -> {
                    int success = DataRepository.getInstance(AppDatabase.getInstance(getActivity(), new AppExecutors()))
                            .updateTask(taskEntity);
                    Log.d("aaaaa", "onClick: success update = " + success);
                    if(success == 1) {
                        getActivity().runOnUiThread(() -> {
                            ((TaskDetailActivity) getActivity()).updateUI();
//                            Intent data = new Intent();
//                            data.putExtra("taskEntity", taskEntity);
                            getActivity().setResult(Constants.EDIT_TASK_SUCCESS);
                            getActivity().onBackPressed();
                        });
                    }
                }).start();
                break;
        }
    }

    private void getInfo() {
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
    }
}
