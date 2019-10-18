package com.sticket.app.sticket.activities.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.databinding.ActivityAccountBinding;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.dto.request.user.UserUpdateRequest;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        initData();
        initViews();
    }

    private void initData() {
        ApiClient.getInstance().getUserService()
                .getMyInfo()
                .enqueue(SimpleCallbackUtil.getSimpleCallback(user -> {
                    binding.setData(user);
                }));
    }

    private void initViews() {
        initListener();
    }

    private void initListener() {
        binding.toggleAccountEdit.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.editName.setEnabled(true);
                binding.editEmail.setEnabled(true);
                binding.editDescription.setEnabled(true);
            } else {
                UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
                userUpdateRequest.setName(binding.editName.getText().toString());
                userUpdateRequest.setEmail(binding.editEmail.getText().toString());
                userUpdateRequest.setDescription(binding.editDescription.getText().toString());
                ApiClient.getInstance().getUserService()
                        .updateMyInfo(userUpdateRequest)
                        .enqueue(SimpleCallbackUtil.getSimpleCallback(apiMessage -> {
                            binding.editName.setEnabled(false);
                            binding.editEmail.setEnabled(false);
                            binding.editDescription.setEnabled(false);
                        }));
            }
        });

    }


    public void btnToBack(View view) {
        onBackPressed();
    }
}
