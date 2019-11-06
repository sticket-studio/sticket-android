package com.sticket.app.sticket.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.store.store_preview.StorePreviewActivity;
import com.sticket.app.sticket.adapter.viewholders.StoreHomeHomeAssetsViewHolder;
import com.sticket.app.sticket.databinding.ItemStoreAssetBinding;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;

import java.util.List;

public class StoreHomeHomeAssetsAdapter extends RecyclerView.Adapter<StoreHomeHomeAssetsViewHolder> {
    private static final String TAG = StoreHomeHomeAssetsAdapter.class.getSimpleName();

    private List<SimpleAssetResponse> assets;
    private OnPreviewClickListener onPreviewClickListener;
    private Context mContext;


    public StoreHomeHomeAssetsAdapter(List<SimpleAssetResponse> assets) {
        this.assets = assets;
    }

    @NonNull
    @Override
    public StoreHomeHomeAssetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        mContext = parent.getContext();

        ItemStoreAssetBinding itemBinding =
                ItemStoreAssetBinding.inflate(layoutInflater, parent, false);



        return new StoreHomeHomeAssetsViewHolder(itemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull StoreHomeHomeAssetsViewHolder holder, int position) {
        final SimpleAssetResponse item = assets.get(position);

        ItemStoreAssetBinding binding = holder.bind(item);
        binding.setAdapter(this);

        binding.getRoot().setOnClickListener(v ->{
            if(onPreviewClickListener != null){
                onPreviewClickListener.onPreviewClick(binding.getItem());
            }

            Intent intent = new Intent(mContext, StorePreviewActivity.class);
            intent.putExtra("assetName",binding.getItem().getName());
            Log.i("click test",binding.getItem().getId()+"");

            intent.putExtra("assetId", binding.getItem().getId()+"");
            mContext.startActivity(intent);
        });


        Glide.with(binding.getRoot())
                .load(item.getImgUrl())
                .placeholder(R.drawable.basic_cheek_logo1)
                .diskCacheStrategy(DiskCacheStrategy.NONE)// 디스크 캐시 저장 off
                .skipMemoryCache(true)// 메모리 캐시 저장 off
                .into(binding.imgItemAssetPreview);
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public interface OnPreviewClickListener {
        public void onPreviewClick(SimpleAssetResponse asset);
    }

}
