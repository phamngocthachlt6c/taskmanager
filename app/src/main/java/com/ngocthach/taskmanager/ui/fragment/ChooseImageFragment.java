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

/**
 * ${CLASS}
 * Created by tryczson on 27/12/2017.
 */

public class ChooseImageFragment extends DialogFragment {

    @BindView(R.id.chooseImageIconView)
    GridView gridView;

    private OnChangeIconImage onChangeIconImage;

    public static ChooseImageFragment newInstance() {
        ChooseImageFragment f = new ChooseImageFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_image_icon, container, false);
        ButterKnife.bind(this, view);
        getDialog().setTitle(getResources().getString(R.string.text_choose_icon));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> listPath = FileUtils.getListAssetsFilePath(getActivity(), ".png");
        IconImageAdapter adapter = new IconImageAdapter(getActivity(), listPath);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(onChangeIconImage != null) {
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
