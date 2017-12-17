package com.ngocthach.taskmanager.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.ui.activity.MainActivity;

import java.util.Date;

/**
 * Created by ThachPham on 12/17/2017.
 */

public class CalendarFragment extends DialogFragment {
    int mNum;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static CalendarFragment newInstance(int num) {
        CalendarFragment f = new CalendarFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Calendar");
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView calendarView = (CalendarView) v.findViewById(R.id.simpleCalendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull  CalendarView calendarView, int i, int i1, int i2) {
//                Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i
                ((MainActivity) getActivity()).setActionBarTitle(new Date(i - 1900, i1, i2));
                getDialog().cancel();
            }
        });
        return v;
    }
}