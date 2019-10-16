package com.sticket.app.sticket.activities.store.store_home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.models.Asset;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.dto.response.user.UserSimple;
import com.sticket.app.sticket.util.Landmark;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StoreHomeStickerGridAdapter extends BaseAdapter {

    private int icons[] = {
            R.drawable.btn_switch, R.drawable.cheek, R.drawable.heart,
            R.drawable.btn_switch, R.drawable.cheek, R.drawable.nose2, R.drawable.mouth_bottom,
            R.drawable.nose2, R.drawable.btn_switch, R.drawable.cheek, R.drawable.mouth_bottom, R.drawable.cheek,
            R.drawable.btn_switch, R.drawable.cheek, R.drawable.mouth_bottom,
    };

    private Context mContext;
    private String landmark;


    public StoreHomeStickerGridAdapter(Context context) {
        this.mContext = context;
    }
    public StoreHomeStickerGridAdapter(Context context, String landmark) {
        this.mContext = context;
        this.landmark = landmark;
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
            stickerGridView = inflater.inflate(R.layout.item_store_sticker, null);

            ApiClient.getInstance().getAssetService()
                    .searchAssets(0,0, Landmark.CHEEK_LEFT+"",0)
                    .enqueue(new CustomCallback<List<Asset>>() {
                        @Override
                        public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                            List<Asset> as = response.body();
                            if (response.body() == null || as.size() ==0) {
                                // 없는 레이아웃 설정하기
                                Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
                            } else {
                                //고쳐주면 다시하기
                                ImageView imageView = (ImageView) stickerGridView.findViewById(R.id.item_image);
                                Asset a = as.get(position);
                                Glide.with(parent.getContext())
                                        .load(a.getImgUrl())
                                        .into(imageView);

                            }

                        }
                    });

        }
        else {
            stickerGridView = (View) convertView;
        }

        return stickerGridView;
    }
}
