package com.sticket.app.sticket.activities.sticker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Motionticon;
import com.sticket.app.sticket.database.entity.Sticon;

import java.util.List;

public class StickerGridAdapter extends BaseAdapter {

    /* CAMERA Adapter*/

    private int icons[] = {
            R.drawable.left_eye ,R.drawable.right_eye, R.drawable.nose, R.drawable.nose2,
            R.drawable.cheek, R.drawable.mouth_bottom, R.drawable.left_eye2, R.drawable.btn_switch,
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch,
            R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch, R.drawable.btn_switch
    };

    private Context mContext;

    private List<Asset> assetList;
    private List<Sticon> sticonList;
    private List<Motionticon> motionticonList;

    public StickerGridAdapter(Context context) {
        this.mContext = context;

        SticketDatabase sticketDatabase = SticketDatabase.getDatabase(context);
        assetList = sticketDatabase.assetDao().getAllassets();
        sticonList = sticketDatabase.sticonDao().getAllSticon();
        motionticonList = sticketDatabase.motionticonDao().getAllMotionticons();


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
        } else {
            stickerGridView = (View) convertView;
        }

        return stickerGridView;
    }
}
