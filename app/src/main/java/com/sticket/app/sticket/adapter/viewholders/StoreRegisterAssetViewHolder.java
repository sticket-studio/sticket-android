package com.sticket.app.sticket.adapter.viewholders;

import android.support.v7.widget.RecyclerView;

import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.databinding.ItemStoreRegisterAssetBinding;

public class StoreRegisterAssetViewHolder extends RecyclerView.ViewHolder {
    private final ItemStoreRegisterAssetBinding binding;

    public StoreRegisterAssetViewHolder(ItemStoreRegisterAssetBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemStoreRegisterAssetBinding bind(Asset item) {
        binding.setItem(item);
        binding.executePendingBindings();
        return binding;
    }
}
