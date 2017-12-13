package com.ngocthach.taskmanager.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.ui.SwipeViewPager;
import com.ngocthach.taskmanager.ui.adapter.SwipeViewAdapter;
import com.ngocthach.taskmanager.ui.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private SwipeViewPager swipeViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSwipeViewPager();

    }

    private void initSwipeViewPager() {
        swipeViewPager = (SwipeViewPager) findViewById(R.id.swipeView);
        swipeViewPager.setOffscreenPageLimit(4);
        SwipeViewAdapter adapter = new SwipeViewAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "ONE"); // all task on today
        adapter.addFragment(new HomeFragment(), "TWO"); // calendar to choose day -> view task
        adapter.addFragment(new HomeFragment(), "THREE");
        adapter.addFragment(new HomeFragment(), "FOUR");
        adapter.addFragment(new HomeFragment(), "FOUR");
        swipeViewPager.setAdapter(adapter);
        swipeViewPager.setCurrentItem(1);
    }
}
