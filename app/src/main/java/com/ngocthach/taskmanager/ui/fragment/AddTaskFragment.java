package com.ngocthach.taskmanager.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.applandeo.materialcalendarview.CalendarView;
import com.ngocthach.taskmanager.AppExecutors;
import com.ngocthach.taskmanager.DataRepository;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.AppDatabase;
import com.ngocthach.taskmanager.db.entity.TaskEntity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ThachPham on 19/12/2017.
 */

public class AddTaskFragment extends DialogFragment implements View.OnClickListener {

    private EditText taskNameEditText;
    private DatePicker datePicker;
    private Button addTask, cancel;

    public static AddTaskFragment newInstance(int num) {
        AddTaskFragment f = new AddTaskFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getResources().getString(R.string.fragment_add_task_title));
        View v = inflater.inflate(R.layout.fragment_add_task, container, false);
        taskNameEditText = (EditText) v.findViewById(R.id.editTextTaskName);
        datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        addTask = (Button) v.findViewById(R.id.addTaskButton);
        addTask.setOnClickListener(this);
        cancel = (Button) v.findViewById(R.id.cancelButton);
        cancel.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTaskButton:
                TaskEntity taskEntity = new TaskEntity();
                Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                Log.d("aaaaa", "onClick: Fragment addtask date = " + date);
                taskEntity.setDate(date);
                taskEntity.setTitle(taskNameEditText.getText().toString());
                new Thread(() -> DataRepository.getInstance(AppDatabase.getInstance(getActivity(), new AppExecutors()))
                        .insertTask(taskEntity)).start();
                getDialog().cancel();
                break;

            case R.id.cancelButton:
                getDialog().cancel();
                break;
        }
    }
}
