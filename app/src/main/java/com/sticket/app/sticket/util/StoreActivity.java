package com.sticket.app.sticket.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.sticket.app.sticket.R;

public class StoreActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                drawer.closeDrawer(Gravity.LEFT) ;
                break;
            case R.id.nav_my_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreMyItemFragment()).commit();
                drawer.closeDrawer(Gravity.LEFT) ;
                break;
            case R.id.nav_my_page:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreMyPageFragment()).commit();
                drawer.closeDrawer(Gravity.LEFT) ;
                break;
            case R.id.nav_like:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreLikeFragment()).commit();
                drawer.closeDrawer(Gravity.LEFT) ;
                break;
            case R.id.nav_gift_box:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreGiftFragment()).commit();
                drawer.closeDrawer(Gravity.LEFT) ;
                break;
            case R.id.nav_charge:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreChargeFragment()).commit();
                drawer.closeDrawer(Gravity.LEFT) ;
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
