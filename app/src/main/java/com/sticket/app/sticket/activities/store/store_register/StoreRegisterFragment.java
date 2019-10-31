package com.sticket.app.sticket.activities.store.store_register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;

import java.util.ArrayList;
import java.util.List;

public class StoreRegisterFragment extends Fragment {

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new StoreRegisterViewFragment(), "눈");
        adapter.addFrag(new StoreRegisterViewFragment(), "코");
        adapter.addFrag(new StoreRegisterViewFragment(), "입");
        adapter.addFrag(new StoreRegisterViewFragment(), "볼");
        adapter.addFrag(new StoreRegisterViewFragment(), "귀");
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            Bundle bundle = new Bundle(1);
            bundle.putString("landmark",title);
            fragment.setArguments(bundle);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
