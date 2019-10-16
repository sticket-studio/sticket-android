package com.sticket.app.sticket.activities.store.store_home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.StoreHomeHomeAssetsAdapter;
import com.sticket.app.sticket.databinding.FragmentStoreStartBinding;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StoreHomeHomeFragment extends Fragment {

    private FragmentStoreStartBinding binding;
    private List<SimpleAssetResponse> todayAssets = new ArrayList<>();
    private List<SimpleAssetResponse> popularAssets = new ArrayList<>();
    private List<SimpleAssetResponse> newAssets = new ArrayList<>();
    private StoreHomeHomeAssetsAdapter todayAssetsAdapter;
    private StoreHomeHomeAssetsAdapter popularAssetsAdapter;
    private StoreHomeHomeAssetsAdapter newAssetsAdapter;
    private int nextTodayAssetsPage = 1;
    private int nextPopularAssetsPage = 1;
    private int nextNewAssetsPage = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        todayAssetsAdapter = new StoreHomeHomeAssetsAdapter(todayAssets);
        popularAssetsAdapter = new StoreHomeHomeAssetsAdapter(popularAssets);
        newAssetsAdapter = new StoreHomeHomeAssetsAdapter(newAssets);

        getTodayAssets(1);
        getPopularAssets(1);
        getNewAssets(1);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_store_start, container, false);

        // Set RecyclerView to StoreMyPageAssetAdapter
        //TODO: 오른쪽 끝까지 가면 새로운 페이지 가져오는 기능 구현

        binding.todayItemRecycler.setAdapter(todayAssetsAdapter);
        binding.popularItemRecycler.setAdapter(popularAssetsAdapter);
        binding.newItemRecycler.setAdapter(newAssetsAdapter);

        return binding.getRoot();
    }

    private void getTodayAssets(int page) {
        ApiClient.getInstance()
                .getAssetService()
                .getTodayAssets(page)
                .enqueue(new CustomCallback<List<SimpleAssetResponse>>() {
                    @Override
                    public void onResponse(Call<List<SimpleAssetResponse>> call, Response<List<SimpleAssetResponse>> response) {
                        if (response.body() == null) {
                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
                        } else {
                            todayAssets.addAll(response.body());
                            todayAssetsAdapter.notifyDataSetChanged();
                            nextTodayAssetsPage++;
                        }
                    }
                });

    }

    private void getPopularAssets(int page) {
        ApiClient.getInstance()
                .getAssetService()
                .getPopularAssets(page)
                .enqueue(new CustomCallback<List<SimpleAssetResponse>>() {
                    @Override
                    public void onResponse(Call<List<SimpleAssetResponse>> call, Response<List<SimpleAssetResponse>> response) {
                        if (response.body() == null) {
                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
                        } else {
                            popularAssets.addAll(response.body());
                            popularAssetsAdapter.notifyDataSetChanged();
                            nextPopularAssetsPage++;
                        }
                    }
                });
    }

    private void getNewAssets(int page) {
        ApiClient.getInstance()
                .getAssetService()
                .getNewAssets(page)
                .enqueue(new CustomCallback<List<SimpleAssetResponse>>() {
                    @Override
                    public void onResponse(Call<List<SimpleAssetResponse>> call, Response<List<SimpleAssetResponse>> response) {
                        if (response.body() == null) {
                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
                        } else {
                            newAssets.addAll(response.body());
                            newAssetsAdapter.notifyDataSetChanged();
                            nextNewAssetsPage++;
                        }
                    }
                });
    }
}
