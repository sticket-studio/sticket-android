package com.sticket.app.sticket.adapter.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sticket.app.sticket.databinding.ItemStoreAssetBinding;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;

import butterknife.OnClick;

public class StoreHomeHomeAssetsViewHolder extends RecyclerView.ViewHolder {
    private final ItemStoreAssetBinding binding;


    public StoreHomeHomeAssetsViewHolder(ItemStoreAssetBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    public ItemStoreAssetBinding bind(SimpleAssetResponse item) {
        binding.setItem(item);
        binding.executePendingBindings();


        return binding;
    }
}