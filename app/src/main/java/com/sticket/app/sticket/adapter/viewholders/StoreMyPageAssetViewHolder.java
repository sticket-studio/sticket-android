package com.sticket.app.sticket.adapter.viewholders;

import android.support.v7.widget.RecyclerView;

import com.sticket.app.sticket.databinding.ItemStoreStickerBinding;
import com.sticket.app.sticket.models.Asset;

public class StoreMyPageAssetViewHolder extends RecyclerView.ViewHolder {
    private final ItemStoreStickerBinding binding;

    public StoreMyPageAssetViewHolder(ItemStoreStickerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemStoreStickerBinding bind(Asset item) {
        binding.setItem(item);
        binding.executePendingBindings();
        return binding;
    }
}
