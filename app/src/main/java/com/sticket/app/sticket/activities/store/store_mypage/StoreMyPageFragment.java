package com.sticket.app.sticket.activities.store.store_mypage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.util.ViewPagerAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoreMyPageFragment extends Fragment {

    private ImageView backGroundImg;
    private CircleImageView profileImg;
    private ToggleButton likeToggle;
    private TextView nameTxt, introductionTxt;
    private TextView worksTxt, followerTxt, followingTxt;
    private Button settingBtn, backgroundSettingBtn;
    private TabLayout itemCategoryTab;
    private ViewPager itemsViewPager;

    private boolean isMe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_mypage, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view){
        profileImg = view.findViewById(R.id.img_store_mypage_profile);
        likeToggle = view.findViewById(R.id.toggle_store_mypage_like);
        nameTxt = view.findViewById(R.id.txt_store_mypage_name);
        introductionTxt = view.findViewById(R.id.txt_store_mypage_introduction);

        worksTxt = view.findViewById(R.id.txt_store_mypage_works);
        followerTxt = view.findViewById(R.id.txt_store_mypage_follower);
        followingTxt = view.findViewById(R.id.txt_store_mypage_following);

        settingBtn = view.findViewById(R.id.btn_store_mypage_setting);              // Go to activity_account
        itemCategoryTab = view.findViewById(R.id.tab_store_mypage_item_category);
        itemsViewPager = view.findViewById(R.id.viewpager_store_mypage);

        setupViewPager(itemsViewPager);
        itemCategoryTab.setupWithViewPager(itemsViewPager);
        initListener();
    }

    private void initListener(){
        likeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new MyPageItemListFragment(), "눈");
        adapter.addFrag(new MyPageItemListFragment(), "코");
        adapter.addFrag(new MyPageItemListFragment(), "입");
        adapter.addFrag(new MyPageItemListFragment(), "볼");
        adapter.addFrag(new MyPageItemListFragment(), "귀");
        viewPager.setAdapter(adapter);
    }
}
