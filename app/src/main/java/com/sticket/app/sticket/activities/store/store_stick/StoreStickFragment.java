package com.sticket.app.sticket.activities.store.store_stick;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.StoreStickAdapter;
import com.sticket.app.sticket.databinding.FragmentStoreStickBinding;
import com.sticket.app.sticket.models.Advertisement;
import com.sticket.app.sticket.models.Stick;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

import java.util.ArrayList;
import java.util.List;

public class StoreStickFragment extends Fragment {

    private FragmentStoreStickBinding binding;
    private StoreStickAdapter stickAdapter;
    private List<Stick> sticks = new ArrayList<>();
    private List<Advertisement> advertisements = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_stick, container, false);

        initData();
        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        stickAdapter = new StoreStickAdapter(this.sticks);
        stickAdapter.setOnBuyStickListener(newStick -> {
            int currentStick = Integer.parseInt(binding.txtStoreStickerMyStick.getText().toString());
            binding.txtStoreStickerMyStick.setText(String.valueOf(currentStick+newStick));
        });
        binding.rvStoreStickSticks.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvStoreStickSticks.setAdapter(stickAdapter);

        binding.rvStoreStickAdvertisements.setLayoutManager(new GridLayoutManager(getContext(), 2));

    }

    private void initData() {
        if (ApiClient.getInstance().isLoggedIn()) {
            ApiClient.getInstance().getUserService()
                    .getMyInfo()
                    .enqueue(SimpleCallbackUtil.getSimpleCallback(me -> {
                        Glide.with(binding.getRoot())
                                .load(me.getImgUrl())
                                .placeholder(R.drawable.img_profile2)
                                .into(binding.imgStoreStickMyProfile);
                        binding.txtStoreStickerMyStick.setText(String.valueOf(me.getStick()));
                        binding.txtStoreStickerCanChargeStick.setText("0");
                        binding.txtStoreStickerCanBeRemoveStick.setText("0");
                    }));
        }
        ApiClient.getInstance().getStickService()
                .getAllSticks()
                .enqueue(SimpleCallbackUtil.getSimpleCallback(sticks -> {
                    this.sticks.clear();
                    this.sticks.addAll(sticks);
                    stickAdapter.notifyDataSetChanged();
                }));
    }
}
