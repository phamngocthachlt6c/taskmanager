package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ngocthach.taskmanager.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * ${CLASS}
 * Created by ThachPham on 27/12/2017.
 */

public class IconImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> listPathIcon;

    public IconImageAdapter(Context context, List<String> listPathIcon) {
        this.context = context;
        this.listPathIcon = listPathIcon;
    }

    @Override
    public int getCount() {
        return listPathIcon.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(listPathIcon.get(i))
                .error(R.mipmap.ic_launcher)
                .resize(200, 200)
                .into(imageView);
        return imageView;
    }
}
