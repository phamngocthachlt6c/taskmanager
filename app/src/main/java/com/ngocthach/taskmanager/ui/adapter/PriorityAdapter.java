package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.ui.activity.MainActivity;

import java.util.ArrayList;

/**
 * ${CLASS}
 * Created by ThachPham on 21/12/2017.
 */

public class PriorityAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private String[] list;

    public PriorityAdapter(Context context, String[] list) {
        this.list = list;
        activity = context;
    }


    public int getCount() {
        return list.length;
    }

    public Object getItem(int i) {
        return list[i];
    }

    public long getItemId(int i) {
        return (long) i;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(activity);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(list[position]);
        if(list[position].equals(activity.getResources().getStringArray(R.array.priority)[0])) {
            txt.setBackgroundColor(activity.getResources().getColor(R.color.high_priority));
        } else if(list[position].equals(activity.getResources().getStringArray(R.array.priority)[1])) {
            txt.setBackgroundColor(activity.getResources().getColor(R.color.medium_priority));
        } else {
            txt.setBackgroundColor(activity.getResources().getColor(R.color.low_priority));
        }

        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        if(list[i].equals(activity.getResources().getStringArray(R.array.priority)[0])) {
            txt.setBackgroundColor(activity.getResources().getColor(R.color.high_priority));
        } else if(list[i].equals(activity.getResources().getStringArray(R.array.priority)[1])) {
            txt.setBackgroundColor(activity.getResources().getColor(R.color.medium_priority));
        } else {
            txt.setBackgroundColor(activity.getResources().getColor(R.color.low_priority));
        }
        txt.setText(list[i]);
        return txt;
    }

}