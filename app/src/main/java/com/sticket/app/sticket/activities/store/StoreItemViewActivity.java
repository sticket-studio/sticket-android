package com.sticket.app.sticket.activities.store;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sticket.app.sticket.R;

public class StoreItemViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_asset);
    }

    public void btnToBack(View view) {
        onBackPressed();
    }

    public void btnBuy(View view){
        // TODO: 구매 버튼 눌렀을 때 일어날 일들

    }
}
