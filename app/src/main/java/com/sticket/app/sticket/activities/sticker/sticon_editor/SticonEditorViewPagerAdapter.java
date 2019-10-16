package com.sticket.app.sticket.activities.sticker.sticon_editor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sticket.app.sticket.adapter.SticonEditorGridAdapter;
import com.sticket.app.sticket.util.Landmark;

import java.util.ArrayList;
import java.util.List;

public class SticonEditorViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private final List<Landmark> landmarkList = new ArrayList<>();

    public SticonEditorViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void init() {
        landmarkList.add(Landmark.EYE_LEFT);
        landmarkList.add(Landmark.EYE_RIGHT);
        landmarkList.add(Landmark.NOSE);
        landmarkList.add(Landmark.MOUTH);
        landmarkList.add(Landmark.CHEEK_LEFT);
        landmarkList.add(Landmark.CHEEK_RIGHT);
        landmarkList.add(Landmark.EAR_LEFT);
        landmarkList.add(Landmark.EAR_RIGHT);

        for (Landmark landmark : landmarkList) {
            SticonEditorAssetGridFragment fragment = new SticonEditorAssetGridFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("landmark", landmark);
            fragment.setArguments(bundle);

            addFrag(fragment, landmark.getKorName());
        }
    }

    public void init(SticonEditorGridAdapter.OnAssetClickListener onAssetClickListener) {
        init();

        for (Fragment fragment : mFragmentList) {
            if (fragment instanceof SticonEditorAssetGridFragment) {
                ((SticonEditorAssetGridFragment) fragment).setOnAssetClickListener(onAssetClickListener);
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
