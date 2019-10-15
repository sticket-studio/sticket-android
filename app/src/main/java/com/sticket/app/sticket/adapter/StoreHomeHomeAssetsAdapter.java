package com.sticket.app.sticket.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.viewholders.StoreHomeHomeAssetsViewHolder;
import com.sticket.app.sticket.databinding.ItemStoreAssetBinding;
import com.sticket.app.sticket.models.Asset;

import java.util.List;

public class StoreHomeHomeAssetsAdapter extends RecyclerView.Adapter<StoreHomeHomeAssetsViewHolder> {
    private static final String TAG = StoreHomeHomeAssetsAdapter.class.getSimpleName();

    private List<Asset> assets;

    public StoreHomeHomeAssetsAdapter(List<Asset> assets) {
        this.assets = assets;
    }

    @NonNull
    @Override
    public StoreHomeHomeAssetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemStoreAssetBinding itemBinding =
                ItemStoreAssetBinding.inflate(layoutInflater, parent, false);
        return new StoreHomeHomeAssetsViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreHomeHomeAssetsViewHolder holder, int position) {
        final Asset item = assets.get(position);

        ItemStoreAssetBinding binding = holder.bind(item);
        binding.setAdapter(this);

        binding.txtItemAssetName.setText(item.getTheme().getName());
        Glide.with(binding.getRoot())
                .load(item.getImgUrl())
                .placeholder(R.drawable.basic_cheek_logo1)
                .into(binding.imgItemAssetPreview);
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

}
