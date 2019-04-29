package com.sticket.app.sticket.util.store;

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
import com.sticket.app.sticket.util.store.store_home.AssetFragment;
import com.sticket.app.sticket.util.store.store_home.StartFragment;
import com.sticket.app.sticket.util.store.store_home.MotionFragment;
import com.sticket.app.sticket.util.store.store_home.StickerFragment;

import java.util.ArrayList;
import java.util.List;

public class StoreHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_home, container, false);

        ViewPager storeHomeViewPager = (ViewPager) view.findViewById(R.id.store_home_viewpager);
        setupViewPager(storeHomeViewPager);

        TabLayout storeHomeTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        storeHomeTabLayout.setupWithViewPager(storeHomeViewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment
        adapter.addFrag(new StartFragment(), "홈");
        adapter.addFrag(new AssetFragment(), "애셋");
        adapter.addFrag(new StickerFragment(), "스티커");
        adapter.addFrag(new MotionFragment(), "모션티콘");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
