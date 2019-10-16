package com.sticket.app.sticket.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.viewholders.StoreMyPageAssetViewHolder;
import com.sticket.app.sticket.databinding.ItemStoreAssetBinding;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;

import java.util.List;

public class StoreMyPageAssetAdapter extends RecyclerView.Adapter<StoreMyPageAssetViewHolder> {
    private static final String TAG = StoreMyPageAssetAdapter.class.getSimpleName();

    private final List<SimpleAssetResponse> assets;
    private OnAssetClickListener onAssetClickListener;

    public StoreMyPageAssetAdapter(List<SimpleAssetResponse> assets) {
        this.assets = assets;
    }

    @NonNull
    @Override
    public StoreMyPageAssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemStoreAssetBinding itemBinding =
                ItemStoreAssetBinding.inflate(layoutInflater, parent, false);
        return new StoreMyPageAssetViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreMyPageAssetViewHolder holder, int position) {
        final SimpleAssetResponse item = assets.get(position);
        ItemStoreAssetBinding binding = holder.bind(item);
        Glide.with(binding.getRoot())
                .load(item.getImgUrl())
                .placeholder(R.drawable.basic_cheek_logo1)
                .into(binding.imgItemAssetPreview);
        binding.getRoot().setOnClickListener(v -> {
            if (onAssetClickListener != null) {
                onAssetClickListener.onAssetClick(item);
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
        public void onAssetClick(SimpleAssetResponse asset);
    }
}
