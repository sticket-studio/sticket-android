package com.sticket.app.sticket.activities.store;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sign.SigninActivity;
import com.sticket.app.sticket.activities.store.store_viewbyasset.StoreViewByAssetFragment;
import com.sticket.app.sticket.activities.store.store_charge.StoreChargeFragment;
import com.sticket.app.sticket.activities.store.store_home.StoreHomeFragment;
import com.sticket.app.sticket.activities.store.store_like.StoreLikeFagement;
import com.sticket.app.sticket.activities.store.store_mypage.StoreMyPageFragment;
import com.sticket.app.sticket.databinding.ActivityStoreBinding;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.dto.response.user.UserPageResponse;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

public class StoreActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int ACTIVITY_REQ_SIGNIN = 1234;

    private ActivityStoreBinding binding;
    private ImageView profileImg;
    private TextView nameTxt;
    private TextView emailTxt;
    private Button signinBtn;
    private Button signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store);

//        setSupportActionBar(toolbar);         // Delete Sticket Title
        binding.toolbar.setTitle(null);

        binding.navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreHomeFragment()).commit();
            binding.navView.setCheckedItem(R.id.nav_home);
            binding.drawerLayout.closeDrawer(Gravity.LEFT);
        }
        initHeader();
    }

    private void initHeader() {
        View headerView = binding.navView.getHeaderView(0);

        profileImg = headerView.findViewById(R.id.img_store_header_profile);
        nameTxt = headerView.findViewById(R.id.txt_store_header_name);
        emailTxt = headerView.findViewById(R.id.txt_store_header_email);
        signinBtn = headerView.findViewById(R.id.btn_store_header_signin);
        signoutBtn = headerView.findViewById(R.id.btn_store_header_signout);

        signinBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StoreActivity.this, SigninActivity.class);
            startActivityForResult(intent, ACTIVITY_REQ_SIGNIN);
        });

        signoutBtn.setOnClickListener(v -> {
            ApiClient.getInstance().getAuthService()
                    .signout()
                    .enqueue(SimpleCallbackUtil.getSimpleCallback(responseBody -> {
                        ApiClient.getInstance().setUserId(0);
                        checkSignedIn();
                    }));
        });

        setNavigationHeader();
        checkSignedIn();
    }

    private void checkSignedIn() {
        if (ApiClient.getInstance().getUserId() == 0) {
            signinBtn.setVisibility(View.VISIBLE);
            signoutBtn.setVisibility(View.GONE);
            nameTxt.setTextColor(getResources().getColor(R.color.dark_grey));
            nameTxt.setText("로그인 해주세요");
            emailTxt.setText("");
            Glide.with(StoreActivity.this)
                    .load(getResources().getDrawable(R.drawable.img_profile2))
                    .into(profileImg);
        } else {
            nameTxt.setTextColor(getResources().getColor(R.color.black));
            signinBtn.setVisibility(View.GONE);
            signoutBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new StoreHomeFragment()).commit();
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.txtToolbarTitle.setText("홈");
                break;
            case R.id.nav_view_by_asset:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new StoreViewByAssetFragment()).commit();
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.txtToolbarTitle.setText("애셋별 열람");
                break;
            case R.id.nav_my_page:
                StoreMyPageFragment storeMyPageFragment = new StoreMyPageFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(StoreMyPageFragment.EXTRA_USER_IDX, ApiClient.getInstance().getUserId());
                storeMyPageFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, storeMyPageFragment).commit();
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.txtToolbarTitle.setText("마이 페이지");
                break;
            case R.id.nav_like:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new StoreLikeFagement()).commit();
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.txtToolbarTitle.setText("좋아요");
                break;
            case R.id.nav_charge:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new StoreChargeFragment()).commit();
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.txtToolbarTitle.setText("스틱 충전");
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private UserPageResponse user;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQ_SIGNIN) {
            if (resultCode == Activity.RESULT_OK) {
                setNavigationHeader();
            }
        }
    }

    private void setNavigationHeader() {
        ApiClient.getInstance().getUserService()
                .getUserInfoById(ApiClient.getInstance().getUserId())
                .enqueue(SimpleCallbackUtil.getSimpleCallback(responseBody -> {
                    user = responseBody;
                    Glide.with(StoreActivity.this)
                            .load(this.user.getImgUrl())
                            .placeholder(R.drawable.img_profile2)
                            .into(profileImg);
                    nameTxt.setText(user.getName());
                    emailTxt.setText(user.getEmail());
                    checkSignedIn();
                }));
    }
}
