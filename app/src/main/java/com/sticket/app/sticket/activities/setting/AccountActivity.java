package com.sticket.app.sticket.activities.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sign.SigninActivity;

import butterknife.OnClick;

public class AccountActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void btnToBack(View view) {
        onBackPressed();
    }

    public void btnAccount(View view){
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }
}
