package com.sticket.app.sticket.activities.store.store_home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.models.Asset;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;
import com.sticket.app.sticket.retrofit.dto.response.user.UserSimple;
import com.sticket.app.sticket.util.Landmark;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StoreHomeStickerGridAdapter extends BaseAdapter {

    final ArrayList<SimpleAssetResponse> icons = new ArrayList<>();

    private Context mContext;
    private class StoreHomeStickerViewHolder {
        public StoreHomeStickerViewHolder(ImageView assetImg, TextView assetName) {
            this.assetImg= assetImg;
            this.assetName = assetName;

        }

        ImageView assetImg;
        TextView assetName;
    }

    public StoreHomeStickerGridAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.icons.size();
    }
    public void addItem(SimpleAssetResponse sa){
        icons.add(sa);
        Log.i("size",icons.size()+"");
    }

    public void setItems(List<SimpleAssetResponse> icons){
        for(SimpleAssetResponse s : icons){
            icons.add(s);
        }
    }


    @Override
    public Object getItem(int position) {
        return icons.get(position);
    }


    @Override
    public long getItemId(int position) {
        return icons.get(position).getId();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        StoreHomeStickerViewHolder storeHomeStickerViewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.item_store_asset, parent,false);
            ImageView assetImg = (ImageView)convertView.findViewById(R.id.img_item_asset_preview);
            TextView assetName = (TextView)convertView.findViewById(R.id.txt_item_asset_name);
            storeHomeStickerViewHolder = new StoreHomeStickerViewHolder(assetImg,assetName);
            convertView.setTag(storeHomeStickerViewHolder);
        }
        else {
            storeHomeStickerViewHolder = (StoreHomeStickerViewHolder)convertView.getTag();
        }

        SimpleAssetResponse simpleAssetResponse = icons.get(position);
        Glide.with(parent.getContext()).load(simpleAssetResponse.getImgUrl())
                .into(storeHomeStickerViewHolder.assetImg);
        storeHomeStickerViewHolder.assetName.setText(simpleAssetResponse.getName());


        return convertView;
    }
}
