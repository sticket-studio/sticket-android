package com.sticket.app.sticket.activities.store.store_register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.retrofit.dto.request.asset.InsertAssetRequest;
import com.sticket.app.sticket.util.Landmark;

import java.util.ArrayList;
import java.util.List;

public class StoreRegisterFragment extends Fragment {
    private static final String[] korLandmarks = new String[]{"눈", "코", "입", "볼", "귀"};

    private StoreRegisterViewFragment[] storeRegisterViewFragments = new StoreRegisterViewFragment[5];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_register, container, false);

        ViewPager storeViewByAssetViewViewPager = (ViewPager) view.findViewById(R.id.store_register_viewpager);
        setupViewPager(storeViewByAssetViewViewPager);

        TabLayout storeViewByAssetViewTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        storeViewByAssetViewTabLayout.setupWithViewPager(storeViewByAssetViewViewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        SticketDatabase sd = SticketDatabase.getDatabase(getContext());
        List<Asset> allAsset = sd.assetDao().getAllassets();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        for (int i = 0; i < 5; i++) {
            storeRegisterViewFragments[i] = new StoreRegisterViewFragment();
            ArrayList<Asset> tempAssets = new ArrayList<>();

            for (Asset asset : allAsset) {
                if (asset.getType() == Asset.TYPE_OWN && asset.getLandmark() == Landmark.LANDMARKS[i]) {
                    tempAssets.add(asset);
                }
            }

            adapter.addFrag(storeRegisterViewFragments[i], tempAssets,Landmark.LANDMARKS[i]);
        }
        viewPager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, ArrayList<Asset> assets, Landmark landmark) {
            mFragmentList.add(fragment);
            Bundle bundle = new Bundle();
            bundle.putString("landmark", landmark.getKorName());
            bundle.putSerializable(StoreRegisterViewFragment.EXTRA_REGISTER_ASSETS, assets);
            bundle.putSerializable(StoreRegisterViewFragment.EXTRA_REGISTER_LANDMARK, landmark);
            fragment.setArguments(bundle);
            mFragmentTitleList.add(landmark.getKorName());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
