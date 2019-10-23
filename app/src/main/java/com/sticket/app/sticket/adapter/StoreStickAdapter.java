package com.sticket.app.sticket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.viewholders.StoreStickViewHolder;
import com.sticket.app.sticket.databinding.ItemStoreStickBinding;
import com.sticket.app.sticket.models.Stick;
import com.sticket.app.sticket.util.AlertDialogBuilderUtil;

import java.util.List;

public class StoreStickAdapter extends RecyclerView.Adapter<StoreStickViewHolder> {
    private static final String TAG = StoreStickAdapter.class.getSimpleName();

    private Context context;
    private List<Stick> sticks;
    private OnBuyStickListener onBuyStickListener;

    public StoreStickAdapter(List<Stick> sticks) {
        this.sticks = sticks;
    }

    @NonNull
    @Override
    public StoreStickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemStoreStickBinding itemBinding =
                ItemStoreStickBinding.inflate(layoutInflater, parent, false);
        return new StoreStickViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreStickViewHolder holder, final int position) {
        final Stick item = sticks.get(position);

        ItemStoreStickBinding binding = holder.bind(item);
        binding.setAdapter(this);

        Glide.with(binding.getRoot())
                .load(item.getImgUrl())
                .placeholder(R.drawable.basic_cheek_logo1)
                .into(binding.imgItemStickPreview);

        binding.getRoot().setOnClickListener(v -> {
            AlertDialogBuilderUtil.simpleDialog(context, "스틱 구매", "스틱을 구매하시겠습니까?",
                    (dialog, which) -> {
                        if (onBuyStickListener != null) {
                            onBuyStickListener.onBuyStick(item);
                        }
                    });
        });
    }

    public void setOnBuyStickListener(OnBuyStickListener onBuyStickListener) {
        this.onBuyStickListener = onBuyStickListener;
    }

    @Override
    public int getItemCount() {
        return sticks.size();
    }

    public interface OnBuyStickListener {
        public void onBuyStick(Stick stick);
    }

}
