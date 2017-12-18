package com.ngocthach.taskmanager.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.ui.activity.MainActivity;

import java.util.Calendar;
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
        CalendarView calendarView = (CalendarView) v.findViewById(R.id.calendarView);
        calendarView.setDate(Calendar.getInstance().getTime());
        calendarView.setOnDayClickListener(eventDay -> {
            Log.d("aaaaa", "onDayClick: " + eventDay.getCalendar().getTime().toString());
            Date date = new Date(eventDay.getCalendar().getTime().getTime());
            ((MainActivity) getActivity()).setActionBarTitle(date);
            getDialog().cancel();
        });
        return v;
    }
}