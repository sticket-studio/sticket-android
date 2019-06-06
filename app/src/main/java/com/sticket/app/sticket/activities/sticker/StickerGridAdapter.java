package com.sticket.app.sticket.activities.sticker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sticket.app.sticket.R;

public class StickerGridAdapter extends BaseAdapter {

    /* CAMERA Adapter*/

    private int icons[] = {
            R.drawable.left_eye_small, R.drawable.right_eye_small, R.drawable.nose_small, R.drawable.nose2_small,
            R.drawable.cheek, R.drawable.mouth_bottom_small, R.drawable.left_eye2, R.drawable.btn_switch,
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch,
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch
    };

    private Context mContext;

    public StickerGridAdapter(Context context) {
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

    // Holder를 만들자
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View stickerGridView;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            stickerGridView = inflater.inflate(R.layout.item_grid_sticker, null);
            ImageView imageView = (ImageView) stickerGridView.findViewById(R.id.item_sticker_image);
            imageView.setImageResource(icons[position]);
        }
        else {
            stickerGridView = (View) convertView;
        }

        return stickerGridView;
    }
}
