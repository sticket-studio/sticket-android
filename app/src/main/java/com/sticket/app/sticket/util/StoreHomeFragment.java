package com.sticket.app.sticket.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;

public class StoreHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_home, container, false);

//        ViewPager mViewPager = (ViewPager) findViewById(R.id.store_home_viewpager);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.store_home_tab);
//        tabLayout.addTab(tabLayout.newTab().setText("홈"));
//        tabLayout.addTab(tabLayout.newTab().setText("애셋"));
//        tabLayout.addTab(tabLayout.newTab().setText("스티커"));
//        tabLayout.addTab(tabLayout.newTab().setText("모션티콘"));

    }

}
