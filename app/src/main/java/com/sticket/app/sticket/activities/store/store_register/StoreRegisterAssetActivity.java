package com.sticket.app.sticket.activities.store.store_register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sticket.app.sticket.R;

public class StoreRegisterAssetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_asset);
    }

    public void btnToBack(View view) {
        onBackPressed();
    }

    public void btnToRegister(View view){
        // TODO: 완료 버튼 눌렀을 때 일어날 일들

    }
}
