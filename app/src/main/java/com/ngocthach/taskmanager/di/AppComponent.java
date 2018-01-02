package com.ngocthach.taskmanager.di;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.ui.activity.MainActivity;
import com.ngocthach.taskmanager.ui.fragment.AssetsFragment;
import com.ngocthach.taskmanager.ui.fragment.HomeFragment;
import com.ngocthach.taskmanager.ui.fragment.PrincipleFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ${CLASS}
 * Created by ThachPham on 26/12/2017.
 */
@Singleton
@Component(modules = {SharedPreferencesModule.class, ContextModule.class, AssetViewModelModule.class,
        AppExecutorModule.class, PrincipleViewModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(HomeFragment homeFragment);
    void inject(AssetsFragment assetsFragment);
    void inject(MyApplication myApplication);
    void inject(PrincipleFragment principleFragment);
}
