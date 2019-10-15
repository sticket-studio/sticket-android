package com.sticket.app.sticket.activities.store.store_home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sticket.app.sticket.R;

public class StoreHomeStickerGridAdapter extends BaseAdapter {

    private int icons[] = {
            R.drawable.btn_switch, R.drawable.cheek, R.drawable.heart,
            R.drawable.btn_switch, R.drawable.cheek, R.drawable.nose2, R.drawable.mouth_bottom,
            R.drawable.nose2, R.drawable.btn_switch, R.drawable.cheek, R.drawable.mouth_bottom, R.drawable.cheek,
            R.drawable.btn_switch, R.drawable.cheek, R.drawable.mouth_bottom,
    };

    private Context mContext;

    public StoreHomeStickerGridAdapter(Context context) {
        this.mContext = context;
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

        View stickerGridView;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            stickerGridView = inflater.inflate(R.layout.item_store_asset, null);
            ImageView imageView = (ImageView) stickerGridView.findViewById(R.id.img_item_asset_preview);
            imageView.setImageResource(icons[position]);
        }
        else {
            stickerGridView = (View) convertView;
        }

        return stickerGridView;
    }
}
