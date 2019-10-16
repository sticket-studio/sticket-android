package com.sticket.app.sticket.adapter.viewholders;

import android.support.v7.widget.RecyclerView;

import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.databinding.ItemGridStickerBinding;

public class SticonEditorGridViewHolder extends RecyclerView.ViewHolder {
    private final ItemGridStickerBinding binding;

    public SticonEditorGridViewHolder(ItemGridStickerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemGridStickerBinding bind(Asset item) {
        binding.setItem(item);
        binding.executePendingBindings();
        return binding;
    }
}