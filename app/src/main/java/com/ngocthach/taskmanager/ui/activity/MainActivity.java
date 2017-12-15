package com.ngocthach.taskmanager.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.ui.SwipeViewPager;
import com.ngocthach.taskmanager.ui.adapter.SwipeViewAdapter;
import com.ngocthach.taskmanager.ui.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private SwipeViewPager swipeViewPager;
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) {
//            getSupportActionBar().hide();
//        }

        homeFragment = new HomeFragment();
        findViewById(R.id.imgSendLetterDeactive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragment.insertTask();
            }
        });

        initSwipeViewPager();
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
//        swipeViewPager.setCurrentItem(0);
    }

}
