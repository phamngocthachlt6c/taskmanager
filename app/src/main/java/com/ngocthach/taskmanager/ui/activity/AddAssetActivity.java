package com.ngocthach.taskmanager.ui.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ngocthach.taskmanager.MyApplication;
import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.Constants;
import com.ngocthach.taskmanager.db.entity.AssetEntity;
import com.ngocthach.taskmanager.ui.fragment.ChooseImageFragment;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 02/01/2018.
 */

public class AddAssetActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.editTextTaskName)
    EditText taskNameEditText;
    @BindView(R.id.addTaskButton)
    Button addTask;
    @BindView(R.id.cancelButton)
    Button cancel;
    @BindView(R.id.iconView)
    ImageView iconView;

    private int mStackLevel;
    private String iconUrl = Constants.DEFAULT_ASSET_ICON_PATH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setContentView(R.layout.activity_add_asset);
        ButterKnife.bind(this);

        Picasso.with(this).load(Constants.DEFAULT_ASSET_ICON_PATH).error(R.mipmap.ic_launcher).into(iconView);

        addTask.setOnClickListener(this);
        cancel.setOnClickListener(this);
        iconView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTaskButton:
                AssetEntity insertedAsset = getInfo();
                new Thread(() -> ((MyApplication) getApplication()).getRepository()
                        .insertAsset(insertedAsset)).start();
                super.onBackPressed();
                break;

            case R.id.cancelButton:
                setResult(Constants.ADD_TASK_FAILED);
                super.onBackPressed();
                break;

            case R.id.iconView:
                ChooseImageFragment fragment = ChooseImageFragment.newInstance();
                fragment.setAssetType(ChooseImageFragment.Assets.ASSETS);
                fragment.setOnChangeIconImage(path -> {
                    iconUrl = path;
                    Picasso.with(AddAssetActivity.this).load(iconUrl)
                            .error(R.mipmap.ic_launcher)
                            .into(iconView);
                });
                showDialog(fragment);
                break;
        }
    }

    private AssetEntity getInfo() {
        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setIconUrl(iconUrl);
        assetEntity.setName(taskNameEditText.getText().toString());
        return assetEntity;
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
}
