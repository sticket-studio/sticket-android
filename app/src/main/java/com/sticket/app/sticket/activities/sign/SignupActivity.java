package com.sticket.app.sticket.activities.sign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.dto.request.auth.SignupRequest;
import com.sticket.app.sticket.retrofit.message.ApiMessasge;
import com.sticket.app.sticket.util.Alert;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final int FOLDERPICKER_CODE = 123;

    @BindView(R.id.edit_signup_email)
    EditText emailEdit;
    @BindView(R.id.edit_signup_username)
    EditText usernameEdit;
    @BindView(R.id.edit_signup_password)
    EditText passwordEdit;
    @BindView(R.id.edit_signup_password_confirm)
    EditText passwordConfirmEdit;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup_submit)
    void signupSubmit(View view) {
        if (!passwordEdit.getText().toString().equals(passwordConfirmEdit.getText().toString())) {
            Alert.makeText("두 비밀번호가 일치하지 않습니다");
            return;
        }
        SignupRequest request = new SignupRequest();
        request.setEmail(emailEdit.getText().toString());
        request.setName(usernameEdit.getText().toString());
        request.setPassword(passwordEdit.getText().toString());

        ApiClient.getInstance().getAuthService()
                .userSignUp(request)
                .enqueue(SimpleCallbackUtil.getSimpleCallback());
    }
}
