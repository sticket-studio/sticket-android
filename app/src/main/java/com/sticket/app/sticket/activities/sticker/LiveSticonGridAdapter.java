package com.sticket.app.sticket.activities.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Sticon;

import java.util.List;

public class LiveSticonGridAdapter extends BaseAdapter {

    /* CAMERA Adapter*/

    private Context mContext;

    private List<Sticon> sticonList;

    public LiveSticonGridAdapter(Context context) {
        this.mContext = context;

        SticketDatabase sticketDatabase = SticketDatabase.getDatabase(context);
        sticonList = sticketDatabase.sticonDao().getAllSticon();
    }

    @Override
    public int getCount() {
        return sticonList.size();
    }

    @Override
    public Object getItem(int position) {
        return sticonList.get(position);
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
            Bitmap bitmap = BitmapFactory.decodeFile(sticonList.get(position).getLocal_url());
            stickerGridView = inflater.inflate(R.layout.item_grid_sticker, null);
            ImageView imageView = (ImageView) stickerGridView.findViewById(R.id.item_sticker_image);
            imageView.setImageBitmap(bitmap);
        } else {
            stickerGridView = (View) convertView;
        }

        return stickerGridView;
    }
}
