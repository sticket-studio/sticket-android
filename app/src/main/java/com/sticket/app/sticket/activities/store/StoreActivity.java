package com.sticket.app.sticket.activities.store;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.store.store_viewbyasset.StoreViewByAssetFragment;
import com.sticket.app.sticket.activities.store.store_charge.StoreChargeFragment;
import com.sticket.app.sticket.activities.store.store_home.StoreHomeFragment;
import com.sticket.app.sticket.activities.store.store_like.StoreLikeFagement;
import com.sticket.app.sticket.activities.store.store_mypage.StoreMyPageFragment;
import com.sticket.app.sticket.retrofit.client.ApiClient;

public class StoreActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        tv = findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);         // Delete Sticket Title
        toolbar.setTitle(null);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            drawer.closeDrawer(Gravity.LEFT) ;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreHomeFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                tv.setText("홈");
                break;
            case R.id.nav_view_by_asset:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreViewByAssetFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                tv.setText("애셋별 열람");
                break;
            case R.id.nav_my_page:
                StoreMyPageFragment storeMyPageFragment = new StoreMyPageFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(StoreMyPageFragment.EXTRA_USER_IDX, ApiClient.getInstance().getUserId());
                storeMyPageFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, storeMyPageFragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                tv.setText("마이 페이지");
                break;
            case R.id.nav_like:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreLikeFagement()).commit();
                drawer.closeDrawer(GravityCompat.START);
                tv.setText("좋아요");
                break;
            case R.id.nav_charge:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreChargeFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                tv.setText("스틱 충전");
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

}
