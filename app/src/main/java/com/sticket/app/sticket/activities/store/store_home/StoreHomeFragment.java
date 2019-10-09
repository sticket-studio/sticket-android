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
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.databinding.FragmentStoreStartBinding;
import com.sticket.app.sticket.models.Asset;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StoreHomeFragment extends Fragment {

    private FragmentStoreStartBinding binding;
    private List<Asset> todayAssets = new ArrayList<>();
    private List<Asset> popularAssets = new ArrayList<>();
    private List<Asset> newAssets = new ArrayList<>();
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
        binding = binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_store_start, container, false);

        // Set RecyclerView to ItemAdapter
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
                .enqueue(new CustomCallback<List<Asset>>() {
                    @Override
                    public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
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
                .enqueue(new CustomCallback<List<Asset>>() {
                    @Override
                    public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
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
                .enqueue(new CustomCallback<List<Asset>>() {
                    @Override
                    public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
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

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_store_home, container, false);
//
//        ViewPager storeHomeViewPager = (ViewPager) view.findViewById(R.id.store_home_viewpager);
//        setupViewPager(storeHomeViewPager);
//
//        TabLayout storeHomeTabLayout = (TabLayout) view.findViewById(R.id.tabs);
//        storeHomeTabLayout.setupWithViewPager(storeHomeViewPager);
//
//        return view;
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment
//        adapter.addFrag(new StoreHomeHomeFragment(), "홈");
//        adapter.addFrag(new StoreHomeAssetFragment(), "애셋");
//        adapter.addFrag(new StoreHomeStickerFragment(), "스티콘");
//        adapter.addFrag(new StoreHomeMotionFragment(), "모션티콘");
//        viewPager.setAdapter(adapter);
//    }
//
//    public class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFrag(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }

}
