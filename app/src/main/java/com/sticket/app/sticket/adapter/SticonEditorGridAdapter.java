package com.sticket.app.sticket.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.adapter.viewholders.SticonEditorGridViewHolder;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.databinding.ItemGridStickerBinding;

import java.util.List;

public class SticonEditorGridAdapter extends RecyclerView.Adapter<SticonEditorGridViewHolder> {
    private static final String TAG = SticonEditorGridAdapter.class.getSimpleName();

    private final List<Asset> assets;
    private OnAssetClickListener onAssetClickListener;

    public SticonEditorGridAdapter(List<Asset> assets) {
        this.assets = assets;
    }

    @NonNull
    @Override
    public SticonEditorGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemGridStickerBinding itemBinding =
                ItemGridStickerBinding.inflate(layoutInflater, parent, false);
        return new SticonEditorGridViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SticonEditorGridViewHolder holder, int position) {
        final Asset item = assets.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(item.getLocalUrl());
        ItemGridStickerBinding binding = holder.bind(item);
        binding.setAdapter(this);
        binding.itemStickerImage.setImageBitmap(bitmap);
        binding.getRoot().setOnClickListener(v -> {
            if (onAssetClickListener != null) {
                onAssetClickListener.onAssetClick(binding.getItem());
            }
        });
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public void setOnAssetClickListener(OnAssetClickListener onAssetClickListener) {
        this.onAssetClickListener = onAssetClickListener;
    }

    public interface OnAssetClickListener {
        public void onAssetClick(Asset asset);
    }
}
