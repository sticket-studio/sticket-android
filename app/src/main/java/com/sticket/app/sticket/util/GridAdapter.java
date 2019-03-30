package com.sticket.app.sticket.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sticket.app.sticket.R;

public class GridAdapter extends BaseAdapter {

    int icons[] = {
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch,
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch,
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch,
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch,
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch
    };

    private Context mContext;

    public GridAdapter(Context context) {
        this.mContext =context;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return icons[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_image, null);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_img);
            imageView.setImageResource(icons[position]);
        }
        else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
