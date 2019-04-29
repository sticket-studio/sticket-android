package com.sticket.app.sticket.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sticket.app.sticket.R;

import java.util.ArrayList;
import java.util.List;


public class StickerDialog extends BottomSheetDialogFragment {

    private Button btnCapture;      // TODO : Search Custom Listener
    private View view;

    public StickerDialog() {
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.BaseDialogTheme);
    }

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_sticker, container, false);

        ViewPager stickerDialogViewPager = (ViewPager) view.findViewById(R.id.sticker_dialog_viewpager);
        setupViewPager(stickerDialogViewPager);

        TabLayout stickerDialogTabLayout = (TabLayout) view.findViewById(R.id.sticker_dialog_tab);
        stickerDialogTabLayout.setupWithViewPager(stickerDialogViewPager);

        // OnClickListener for dismiss
        LinearLayout layoutBtnEditor =  (LinearLayout) view.findViewById(R.id.layoutStickerEditor);
        layoutBtnEditor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment
        adapter.addFrag(new StickerGridFragment(), "눈");
        adapter.addFrag(new StickerGridFragment(), "코");
        adapter.addFrag(new StickerGridFragment(), "입");
        adapter.addFrag(new StickerGridFragment(), "볼");
        adapter.addFrag(new StickerGridFragment(), "귀걸이");
        adapter.addFrag(new StickerGridFragment(), "스티커");
        adapter.addFrag(new StickerGridFragment(), "모션티콘");
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
