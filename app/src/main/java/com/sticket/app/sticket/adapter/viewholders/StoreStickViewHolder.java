package com.sticket.app.sticket.adapter.viewholders;

import android.support.v7.widget.RecyclerView;

import com.sticket.app.sticket.databinding.ItemStoreAssetBinding;
import com.sticket.app.sticket.databinding.ItemStoreStickBinding;
import com.sticket.app.sticket.models.Stick;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;

public class StoreStickViewHolder extends RecyclerView.ViewHolder {
    private final ItemStoreStickBinding binding;

    public StoreStickViewHolder(ItemStoreStickBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemStoreStickBinding bind(Stick item) {
        binding.setItem(item);
        binding.executePendingBindings();
        return binding;
    }
}