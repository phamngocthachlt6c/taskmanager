package com.ngocthach.taskmanager.ui.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.ui.view.SwipeViewPager;
import com.ngocthach.taskmanager.ui.adapter.SwipeViewAdapter;
import com.ngocthach.taskmanager.ui.fragment.CalendarFragment;
import com.ngocthach.taskmanager.ui.fragment.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipeView)
    SwipeViewPager swipeViewPager;

    private HomeFragment homeFragment;
    private Date currentDate;

    private int mStackLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        currentDate = Calendar.getInstance().getTime();
        setActionBarTitle(currentDate);
        homeFragment = new HomeFragment();
        initSwipeViewPager();
    }

    public void setActionBarTitle(Date date) {
        currentDate = date;
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            actionBar.setTitle(fmtOut.format(date));
        }
        if(homeFragment != null) {
            homeFragment.changeListTask(date);
        }
    }

    private void initSwipeViewPager() {
        swipeViewPager.setOffscreenPageLimit(4);
        SwipeViewAdapter adapter = new SwipeViewAdapter(getSupportFragmentManager());
        adapter.addFragment(homeFragment, "ONE"); // all task on today
        adapter.addFragment(new HomeFragment(), "TWO"); // chart
        adapter.addFragment(new HomeFragment(), "THREE");
        adapter.addFragment(new HomeFragment(), "FOUR");
        adapter.addFragment(new HomeFragment(), "FOUR");
        swipeViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_calendar:
                DialogFragment calendarFragment = CalendarFragment.newInstance(mStackLevel);
                showDialog(calendarFragment);
                return true;

            case R.id.action_add_task:
                Intent intent = new Intent(this, AddTaskActivity.class);
                startActivityForResult(intent, Constants.ADD_TASK_REQUEST);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.ADD_TASK_REQUEST) {
            if(resultCode == Constants.ADD_TASK_SUCCESS) {
                homeFragment.changeListTask(currentDate);
            }
        }
    }
}
