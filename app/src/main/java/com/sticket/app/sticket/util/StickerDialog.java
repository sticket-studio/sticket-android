package com.sticket.app.sticket.util;

import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.sticket.app.sticket.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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

//        new UserLockBottomSheetBehavior();

        View view = inflater.inflate(R.layout.dialog_sticker, container, false);

        ViewPager stickerDialogViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(stickerDialogViewPager);

        TabLayout stickerDialogTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        stickerDialogTabLayout.setupWithViewPager(stickerDialogViewPager);


        // Dismiss
        RelativeLayout layoutBtnEditor =  (RelativeLayout) view.findViewById(R.id.layoutBtnEditor);
        layoutBtnEditor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Editor Toggle Button
        ToggleButton btnEditor = (ToggleButton) view.findViewById(R.id.btnEditor);
        final RelativeLayout layoutStickerEditor = (RelativeLayout) view.findViewById(R.id.layoutStickerEditor);

        btnEditor.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                if(isChecked){
                    layoutStickerEditor.setVisibility(VISIBLE);

                } else{
                    layoutStickerEditor.setVisibility(GONE);
                }
            }
        });
        return view;
    }

    @Override public void onStart() {
        super.onStart();

        // Set Dialog without Dim
        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams windowParams = window.getAttributes();
//        windowParams.dimAmount = 0;
//        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        window.setAttributes(windowParams);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
