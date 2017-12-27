package com.ngocthach.taskmanager.di;

import com.ngocthach.taskmanager.ui.activity.MainActivity;
import com.ngocthach.taskmanager.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * ${CLASS}
 * Created by tryczson on 26/12/2017.
 */

@Component(modules = {SharedPreferencesModule.class, ContextModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(HomeFragment homeFragment);
}
