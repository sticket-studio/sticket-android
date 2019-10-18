package com.sticket.app.sticket.activities.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.ApiConfig;
import com.sticket.app.sticket.retrofit.dto.request.auth.SignInRequest;
import com.sticket.app.sticket.util.Preference;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Credentials;

public class SigninActivity extends AppCompatActivity {
    private static final int FOLDERPICKER_CODE = 123;
    public static final String EXTRA_USER = "USER";

    @BindView(R.id.check_signin_auto_sigin_in)
    CheckBox autoSignInCheck;
    @BindView(R.id.edit_signin_email)
    EditText emailEdit;
    @BindView(R.id.edit_signin_password)
    EditText passwordEdit;
    private String email, password;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signin_submit)
    void signinSubmit(View view) {
        final SignInRequest request = new SignInRequest();
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
        request.setUsername(email);
        request.setPassword(password);

        ApiClient.getInstance().getAuthService()
                .getToken(Credentials.basic(ApiConfig.USER_NAME, ApiConfig.USER_SECRET),
                        request.getUsername(), request.getPassword(), request.getGrantType())
                .enqueue(SimpleCallbackUtil.getSimpleCallback(signinResponse -> {
                    ApiClient.getInstance().setToken(signinResponse.getAccessToken());
                    ApiClient.getInstance().setUserId(signinResponse.getUserId());

                    if (!autoSignInCheck.isChecked()) {
                        email = null;
                        password = null;
                    }

                    Preference.getInstance().putString(Preference.PREFERENCE_NAME_EMAIL, email);
                    Preference.getInstance().putString(Preference.PREFERENCE_NAME_PASSWORD, password);

                    setResult(RESULT_OK);
                    finish();
                }));
    }

    @OnClick(R.id.btn_signin_signup)
    void signupSubmit(View view) {
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
