package com.sticket.app.sticket.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.viewholders.StoreHomeHomeAssetsViewHolder;
import com.sticket.app.sticket.adapter.viewholders.StoreStickViewHolder;
import com.sticket.app.sticket.databinding.ItemStoreAssetBinding;
import com.sticket.app.sticket.databinding.ItemStoreStickBinding;
import com.sticket.app.sticket.models.Stick;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;

import java.util.List;

public class StoreStickAdapter extends RecyclerView.Adapter<StoreStickViewHolder> {
    private static final String TAG = StoreStickAdapter.class.getSimpleName();

    private List<Stick> sticks;

    public StoreStickAdapter(List<Stick> sticks) {
        this.sticks = sticks;
    }

    @NonNull
    @Override
    public StoreStickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStoreStickBinding itemBinding =
                ItemStoreStickBinding.inflate(layoutInflater, parent, false);
        return new StoreStickViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreStickViewHolder holder, int position) {
        final Stick item = sticks.get(position);

        ItemStoreStickBinding binding = holder.bind(item);
        binding.setAdapter(this);

        Glide.with(binding.getRoot())
                .load(item.getImgUrl())
                .placeholder(R.drawable.basic_cheek_logo1)
                .into(binding.imgItemStickPreview);
    }

    @Override
    public int getItemCount() {
        return sticks.size();
    }

}
