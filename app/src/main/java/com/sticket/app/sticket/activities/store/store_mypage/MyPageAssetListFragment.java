package com.sticket.app.sticket.activities.store.store_mypage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.StoreMyPageAssetAdapter;
import com.sticket.app.sticket.databinding.FragmentStoreMypageViewpagerBinding;
import com.sticket.app.sticket.models.Asset;

import java.util.ArrayList;
import java.util.List;

public class MyPageAssetListFragment extends Fragment {
    public static final String EXTRA_ASSETS = "ASSETS";

    private FragmentStoreMypageViewpagerBinding binding;
    private List<Asset> assets = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_store_mypage_viewpager, container, false);

        assets = (List<Asset>) getArguments().getSerializable(EXTRA_ASSETS);

        StoreMyPageAssetAdapter storeMyPageAssetAdapter = new StoreMyPageAssetAdapter(assets);
        binding.recyclerItemList.setAdapter(storeMyPageAssetAdapter);

        return binding.getRoot();
    }
}
