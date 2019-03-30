package com.sticket.app.sticket.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;

import com.sticket.app.sticket.R;

import java.util.ArrayList;
import java.util.List;

public class StickerDialog extends BottomSheetDialogFragment {

    private TabLayout stickerDialogTabLayout;
    private ViewPager stickerDialogViewPager;
    private Button btnCapture;      // TODO : Search Custom Listener

    private GridView gridView;

    int icons[] = {
            R.drawable.btn_switch
    };

    @Override public void onStart() {
        super.onStart();

        // Set Dialog without Dim
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_sticker, container, false);

        stickerDialogViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(stickerDialogViewPager);

        stickerDialogTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        stickerDialogTabLayout.setupWithViewPager(stickerDialogViewPager);

        btnCapture = (Button) view.findViewById(R.id.btnCapture);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment
        adapter.addFrag(new GridFragment(), "눈");
        adapter.addFrag(new GridFragment(), "코");
        adapter.addFrag(new GridFragment(), "입");
        adapter.addFrag(new GridFragment(), "볼");
        adapter.addFrag(new GridFragment(), "귀걸이");
        adapter.addFrag(new GridFragment(), "스티커");
        adapter.addFrag(new GridFragment(), "모션티콘");
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
