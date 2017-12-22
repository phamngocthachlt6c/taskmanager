package com.ngocthach.taskmanager.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.entity.TaskEntity;
import com.ngocthach.taskmanager.ui.activity.TaskDetailActivity;
import com.ngocthach.taskmanager.ui.adapter.PriorityAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 22/12/2017.
 */

public class EditTaskFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_task, container, false);
        ButterKnife.bind(this, view);
        TaskEntity taskEntity = ((TaskDetailActivity) getActivity()).getTaskEntity();
        toolbar.setVisibility(View.GONE);
        addTask.setText(getResources().getString(R.string.text_edit_task));
        if(taskEntity != null) {
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
}
