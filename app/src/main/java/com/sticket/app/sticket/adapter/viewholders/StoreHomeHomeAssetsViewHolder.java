package com.sticket.app.sticket.adapter.viewholders;

import android.support.v7.widget.RecyclerView;

import com.sticket.app.sticket.databinding.ItemStoreAssetBinding;
import com.sticket.app.sticket.models.Asset;

public class StoreHomeHomeAssetsViewHolder extends RecyclerView.ViewHolder {
    private final ItemStoreAssetBinding binding;

    public StoreHomeHomeAssetsViewHolder(ItemStoreAssetBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemStoreAssetBinding bind(Asset item) {
        binding.setItem(item);
        binding.executePendingBindings();
        return binding;
    }
}