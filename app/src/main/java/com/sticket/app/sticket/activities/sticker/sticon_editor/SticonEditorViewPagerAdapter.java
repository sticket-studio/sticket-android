package com.sticket.app.sticket.activities.sticker.sticon_editor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SticonEditorViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private final List<String> defaultTitles = Arrays.asList("눈", "코", "입", "볼", "귀걸이");

    public SticonEditorViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void init() {
        for(String defaultTitle : defaultTitles){
            addFrag(new SticonEditorAssetGridFragment(), defaultTitle);
        }
    }

    public void init(AdapterView.OnItemClickListener onItemClickListener) {
        init();

        for (Fragment fragment : mFragmentList) {
            if (fragment instanceof SticonEditorAssetGridFragment) {
                ((SticonEditorAssetGridFragment) fragment).setOnItemClickListener(onItemClickListener);
            }
        }
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
