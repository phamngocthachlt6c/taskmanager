package com.ngocthach.taskmanager.ui.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.common.MySharedPreferences;
import com.ngocthach.taskmanager.ui.fragment.AssetsFragment;
import com.ngocthach.taskmanager.ui.fragment.BookFragment;
import com.ngocthach.taskmanager.ui.fragment.PrincipleFragment;
import com.ngocthach.taskmanager.ui.view.SwipeViewPager;
import com.ngocthach.taskmanager.ui.adapter.SwipeViewAdapter;
import com.ngocthach.taskmanager.ui.fragment.CalendarFragment;
import com.ngocthach.taskmanager.ui.fragment.HomeFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipeView)
    SwipeViewPager swipeViewPager;
    @BindView(R.id.actionbarTitle)
    TextView actionbarTitle;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    private Menu mOptionMenu;

    private HomeFragment mHomeFragment;
    private AssetsFragment assetsFragment;
    private PrincipleFragment principleFragment;
    private BookFragment mBookFragment;
    private int mStackLevel;
    @Inject
    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set empty action bar title
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("");
        }
        mHomeFragment = new HomeFragment();
        assetsFragment = new AssetsFragment();
        principleFragment = new PrincipleFragment();
        mBookFragment = new BookFragment();
        initSwipeViewPager();
        initTabPager();
        ((MyApplication) getApplicationContext()).getMyComponent().inject(this);

    }

    private void initTabPager() {
        mTabLayout.setupWithViewPager(swipeViewPager);
        mTabLayout.getTabAt(0).setIcon(R.mipmap.icon_home);
        mTabLayout.getTabAt(1).setIcon(R.mipmap.icon_asset);
        mTabLayout.getTabAt(2).setIcon(R.mipmap.icon_principle);
        mTabLayout.getTabAt(3).setIcon(R.mipmap.icon_book);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setVisibleControlOptionMenu(Constants.TAB_HOME);
                        break;
                    case 1:
                        setVisibleControlOptionMenu(Constants.TAB_ASSETS);
                        break;
                    case 2:
                        setVisibleControlOptionMenu(Constants.TAB_PRINCIPLE);
                        break;
                    case 3:
                        setVisibleControlOptionMenu(Constants.TAB_BOOK);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initSwipeViewPager() {
        swipeViewPager.setOffscreenPageLimit(4);
        SwipeViewAdapter adapter = new SwipeViewAdapter(getSupportFragmentManager());
        adapter.addFragment(mHomeFragment, "HOME"); // all task on today
        adapter.addFragment(assetsFragment, "ASSETS"); // chart
        adapter.addFragment(principleFragment, "PRINCIPLES");
        adapter.addFragment(mBookFragment, "BOOKS");
        swipeViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        mOptionMenu = menu;
        setVisibleControlOptionMenu(Constants.TAB_HOME);
        return true;
    }

    private void setVisibleControlOptionMenu(int tab) {
        if(mOptionMenu == null) {
            return;
        }
        switch (tab) {
            case Constants.TAB_HOME:
                mOptionMenu.findItem(R.id.action_add_task).setVisible(true);
                mOptionMenu.findItem(R.id.action_calendar).setVisible(true);
                mOptionMenu.findItem(R.id.action_add_asset).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_principle).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_book).setVisible(false);
                actionbarTitle.setText(getResources().getString(R.string.home_page));
                break;
            case Constants.TAB_ASSETS:
                mOptionMenu.findItem(R.id.action_add_principle).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_task).setVisible(false);
                mOptionMenu.findItem(R.id.action_calendar).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_book).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_asset).setVisible(true);
                actionbarTitle.setText(getResources().getString(R.string.assets_page));
                break;
            case Constants.TAB_PRINCIPLE:
                mOptionMenu.findItem(R.id.action_add_principle).setVisible(true);
                mOptionMenu.findItem(R.id.action_add_task).setVisible(false);
                mOptionMenu.findItem(R.id.action_calendar).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_asset).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_book).setVisible(false);
                actionbarTitle.setText(getResources().getString(R.string.principles_page));
                break;
            case Constants.TAB_BOOK:
                mOptionMenu.findItem(R.id.action_add_principle).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_task).setVisible(false);
                mOptionMenu.findItem(R.id.action_calendar).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_asset).setVisible(false);
                mOptionMenu.findItem(R.id.action_add_book).setVisible(true);
                actionbarTitle.setText(getResources().getString(R.string.book_page));
                break;
        }
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
            case R.id.action_add_asset:
                startActivity(new Intent(this, AddAssetActivity.class));
                return true;
            case R.id.action_add_principle:
                startActivity(new Intent(this, AddPrincipleActivity.class));
                return true;
            case R.id.action_add_book:
                startActivity(new Intent(this, AddBookActivity.class));
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
        switch (requestCode) {
            case Constants.ADD_TASK_REQUEST:
                if(resultCode == Constants.ADD_TASK_SUCCESS) {
                    mHomeFragment.insertTaskToList(data.getParcelableExtra("taskEntity"));
                }
                break;
            case Constants.EDIT_TASK_REQUEST:
                if(resultCode == Constants.EDIT_TASK_SUCCESS) {
                    mHomeFragment.refreshList();
                }
                break;
        }
    }

    public HomeFragment getHomeFragment() {
        return mHomeFragment;
    }
}
