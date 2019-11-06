package com.sticket.app.sticket.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.viewholders.StoreRegisterAssetViewHolder;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.databinding.ItemStoreRegisterAssetBinding;
import com.sticket.app.sticket.retrofit.dto.request.asset.InsertAssetRequest;

import java.util.List;

public class StoreRegisterAssetAdapter extends RecyclerView.Adapter<StoreRegisterAssetViewHolder> {
    private static final String TAG = StoreRegisterAssetAdapter.class.getSimpleName();

    private final List<Asset> insertAssetRequests;
    private OnAssetClickListener onAssetClickListener;

    public StoreRegisterAssetAdapter(List<Asset> insertAssetRequests) {
        this.insertAssetRequests = insertAssetRequests;
    }

    @NonNull
    @Override
    public StoreRegisterAssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStoreRegisterAssetBinding itemBinding =
                ItemStoreRegisterAssetBinding.inflate(layoutInflater, parent, false);
        return new StoreRegisterAssetViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreRegisterAssetViewHolder holder, int position) {
        final Asset item = insertAssetRequests.get(position);
        ItemStoreRegisterAssetBinding binding = holder.bind(item);
        Glide.with(binding.getRoot())
                .load(item.getLocalUrl())
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
        return insertAssetRequests.size();
    }

    public void setOnAssetClickListener(OnAssetClickListener onAssetClickListener) {
        this.onAssetClickListener = onAssetClickListener;
    }

    public interface OnAssetClickListener {
        public void onAssetClick(Asset asset);
    }
}
