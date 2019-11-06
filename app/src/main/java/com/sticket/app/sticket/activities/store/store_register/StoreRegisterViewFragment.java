package com.sticket.app.sticket.activities.store.store_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.StoreRegisterAssetAdapter;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.util.Landmark;

import java.util.ArrayList;
import java.util.List;

public class StoreRegisterViewFragment extends Fragment {
    public static final String EXTRA_REGISTER_ASSETS = "EXTRA_REGISTER_ASSETS";
    public static final String EXTRA_REGISTER_LANDMARK = "EXTRA_REGISTER_LANDMARK";

    private StoreRegisterAssetAdapter storeRegisterAssetAdapter;
    private Landmark landmark;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sticker, container, false);
        RecyclerView storeStickerRV = view.findViewById(R.id.storeStickerGridView);

        landmark = (Landmark) getArguments().getSerializable(EXTRA_REGISTER_LANDMARK);
        List<Asset> assets = (ArrayList) getArguments().getSerializable(EXTRA_REGISTER_ASSETS);

        storeRegisterAssetAdapter = new StoreRegisterAssetAdapter(assets);
        storeStickerRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        storeStickerRV.setAdapter(storeRegisterAssetAdapter);

       storeRegisterAssetAdapter.notifyDataSetChanged();

        storeRegisterAssetAdapter.setOnAssetClickListener(v -> {
            Intent intent = new Intent(getActivity(), StoreRegisterAssetActivity.class);
            intent.putExtra(StoreRegisterAssetActivity.EXTRA_INSERT_ASSET, v);
            startActivity(intent);
        });
        return view;
    }
}
