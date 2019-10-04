package com.sticket.app.sticket.activities.sign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.api.retrofit.client.ApiClient;
import com.sticket.app.sticket.api.retrofit.dto.request.auth.SignupRequest;
import com.sticket.app.sticket.api.retrofit.message.ApiMessasge;
import com.sticket.app.sticket.util.Alert;

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
                .enqueue(new Callback<ApiMessasge>() {
                    @Override
                    public void onResponse(Call<ApiMessasge> call, Response<ApiMessasge> response) {
                        if (response.body() != null) {
                            finish();
                        } else {
                            try {
                                Log.e("SIGNUP", "errorBody : " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Alert.makeText("회원가입 중 에러 발생");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiMessasge> call, Throwable t) {
                        Alert.makeText("회원가입 중 네트워크 에러 발생");
                    }
                });
    }
}
