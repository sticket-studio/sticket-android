package com.sticket.app.sticket.activities.store.store_mypage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.databinding.FragmentStoreMypageBinding;
import com.sticket.app.sticket.models.Asset;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;
import com.sticket.app.sticket.retrofit.dto.response.user.UserPageResponse;
import com.sticket.app.sticket.util.SimpleCallbackUtil;
import com.sticket.app.sticket.util.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StoreMyPageFragment extends Fragment {
    private static final String TAG = StoreMyPageFragment.class.getSimpleName();
    public static final String EXTRA_USER_IDX = "USER_IDX";

    private static final String[] LANDMARKS = {"눈", "코", "입", "볼", "귀"};
    private static final String[] LANDMARKS_ENG = {"EYE_LEFT", "NOSE", "MOUTH", "CHEEK_LEFT", "EAR_LEFT"};
    private FragmentStoreMypageBinding binding;
    private UserPageResponse user;
    private int userIdx;
    private List<Asset> assets = new ArrayList<>();
    private ArrayList<SimpleAssetResponse>[] landmarkAssets = new ArrayList[LANDMARKS_ENG.length];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_mypage, container, false);
        userIdx = getArguments().getInt(EXTRA_USER_IDX);
        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        ApiClient.getInstance().getUserService()
                .getUserInfoById(userIdx)
                .enqueue(SimpleCallbackUtil.getSimpleCallback(user -> {
                    this.user = user;
                    Glide.with(StoreMyPageFragment.this)
                            .load(this.user.getImgUrl())
                            .placeholder(R.drawable.img_profile2)
                            .into(binding.imgStoreMypageProfile);
                    binding.txtStoreMypageName.setText(this.user.getName());
                    binding.txtStoreMypageIntroduction.setText(this.user.getDescription());
                    binding.txtStoreMypageFollower.setText(String.valueOf(user.getFollowerCnt()));
                    binding.txtStoreMypageFollowing.setText(String.valueOf(user.getFollowingCnt()));
                    binding.txtStoreMypageWorks.setText(String.valueOf(this.user.getWorksCnt()));

                    this.assets.addAll(user.getAssets());
                    initList();

                    setupViewPager();
                }));
        binding.tabStoreMypageItemCategory.setupWithViewPager(binding.viewpagerStoreMypage);
        if (isMe()) {
            binding.toggleStoreMypageLike.setVisibility(View.GONE);
            binding.btnStoreMypageSetting.setVisibility(View.VISIBLE);
        } else {
            binding.toggleStoreMypageLike.setVisibility(View.VISIBLE);
            binding.btnStoreMypageSetting.setVisibility(View.GONE);
        }
        initListener();
    }

    private void initListener() {
        binding.toggleStoreMypageLike.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ApiClient.getInstance().getUserService()
                        .likeAuthor(userIdx)
                        .enqueue(SimpleCallbackUtil.getSimpleCallback());
            } else {
                ApiClient.getInstance().getUserService()
                        .dislikeAuthor(userIdx)
                        .enqueue(SimpleCallbackUtil.getSimpleCallback());
            }
        });
    }

    private void initList() {
        for (int i = 0; i < LANDMARKS_ENG.length; i++) {
            landmarkAssets[i] = new ArrayList<>();
        }

        for (Asset asset : assets) {
            for (int i = 0; i < LANDMARKS_ENG.length; i++) {
                if (asset.getLandmark().equals(LANDMARKS_ENG[i])) {
                    landmarkAssets[i].add(SimpleAssetResponse.mapping(asset));
                    break;
                }
            }
        }
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        for (int i = 0; i < LANDMARKS_ENG.length; i++) {
            MyPageAssetListFragment assetListFragment = new MyPageAssetListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(MyPageAssetListFragment.EXTRA_ASSETS, landmarkAssets[i]);
            assetListFragment.setArguments(bundle);
            adapter.addFrag(assetListFragment, LANDMARKS[i]);
        }
        binding.viewpagerStoreMypage.setAdapter(adapter);
        Log.e(TAG, "sticker: " + assets.size());
    }

    private boolean isMe() {
        return this.userIdx == ApiClient.getInstance().getUserId();
    }
}
