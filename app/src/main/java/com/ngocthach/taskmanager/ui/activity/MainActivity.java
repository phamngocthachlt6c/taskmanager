package com.ngocthach.taskmanager.ui.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.ui.SwipeViewPager;
import com.ngocthach.taskmanager.ui.adapter.SwipeViewAdapter;
import com.ngocthach.taskmanager.ui.fragment.CalendarFragment;
import com.ngocthach.taskmanager.ui.fragment.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private SwipeViewPager swipeViewPager;
    private HomeFragment homeFragment;

    private int mStackLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Date date = Calendar.getInstance().getTime();
        setActionBarTitle(date);

        homeFragment = new HomeFragment();
        findViewById(R.id.imgSendLetterDeactive).setOnClickListener(view -> homeFragment.insertTask());

        initSwipeViewPager();
    }

    public void setActionBarTitle(Date date) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            actionBar.setTitle(fmtOut.format(date));
        }
    }

    private void initSwipeViewPager() {
        swipeViewPager = (SwipeViewPager) findViewById(R.id.swipeView);
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
                showCalendarDialog();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    void showCalendarDialog() {
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

        // Create and show the dialog.
        DialogFragment calendarFragment = CalendarFragment.newInstance(mStackLevel);
        calendarFragment.show(ft, "dialog");
    }

}
