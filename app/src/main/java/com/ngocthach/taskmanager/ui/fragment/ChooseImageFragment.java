package com.ngocthach.taskmanager.ui.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.common.FileUtils;
import com.ngocthach.taskmanager.ui.adapter.IconImageAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ngocthach.taskmanager.ui.fragment.ChooseImageFragment.Assets.TASK;

/**
 * ${CLASS}
 * Created by ThachPham on 27/12/2017.
 */

public class ChooseImageFragment extends DialogFragment {

    public enum Assets {
        TASK, ASSETS, PRINCIPLE
    }

    @BindView(R.id.chooseImageIconView)
    GridView gridView;

    private OnChangeIconImage onChangeIconImage;
    private Assets assetType;

    public static ChooseImageFragment newInstance() {
        return new ChooseImageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_image_icon, container, false);
        ButterKnife.bind(this, view);
        switch (assetType) {
            case TASK:
                getDialog().setTitle(getResources().getString(R.string.text_choose_icon));
                break;
            case ASSETS:
                getDialog().setTitle(getResources().getString(R.string.text_choose_icon_asset));
                break;
            case PRINCIPLE:
                getDialog().setTitle(getResources().getString(R.string.text_choose_icon_principle));
                break;
        }
        return view;
    }

    public void setAssetType(Assets asset) {
        assetType = asset;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> listPath;
        switch (assetType) {
            case TASK:
                listPath = FileUtils.getListAssetsFilePath(getActivity(), "icon_tasks", ".png");
                break;
            case ASSETS:
                listPath = FileUtils.getListAssetsFilePath(getActivity(), "icon_assets", ".png");
                break;
            case PRINCIPLE:
                listPath = FileUtils.getListAssetsFilePath(getActivity(), "icon_principles", ".png");
                break;
            default:
                getDialog().cancel();
                return;
        }
        IconImageAdapter adapter = new IconImageAdapter(getActivity(), listPath);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onChangeIconImage != null) {
                    onChangeIconImage.onChangePath(listPath.get(i));
                }
                getDialog().cancel();
            }
        });
    }

    public void setOnChangeIconImage(OnChangeIconImage onChangeIconImage) {
        this.onChangeIconImage = onChangeIconImage;
    }

    public interface OnChangeIconImage {
        void onChangePath(String path);
    }
}
