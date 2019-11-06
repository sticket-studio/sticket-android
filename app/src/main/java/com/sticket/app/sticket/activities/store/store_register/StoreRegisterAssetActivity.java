package com.sticket.app.sticket.activities.store.store_register;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.databinding.ActivityRegisterAssetBinding;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.dto.request.asset.InsertAssetRequest;
import com.sticket.app.sticket.util.Alert;
import com.sticket.app.sticket.util.Landmark;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class StoreRegisterAssetActivity extends AppCompatActivity {
    public static final String EXTRA_INSERT_ASSET = "EXTRA_INSERT_ASSET";

    private ActivityRegisterAssetBinding binding;
    private List<Button> buttons = new ArrayList<>();
    private int[] themeIds = new int[]{19, 20, 21, 22, 23};
    private int selectedThemeId = 19;

    private Asset asset;
    private InsertAssetRequest insertAssetRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_asset);

        asset = (Asset) getIntent().getSerializableExtra(EXTRA_INSERT_ASSET);
        insertAssetRequest = InsertAssetRequest.mapping(asset);

        Glide.with(binding.getRoot())
                .load(insertAssetRequest.getImg())
                .placeholder(R.drawable.basic_cheek_logo1)
                .into(binding.imgRegisterAssetIcon);

        buttons.add(binding.btnRegisterAssetCute);
        buttons.add(binding.btnRegisterAssetFun);
        buttons.add(binding.btnRegisterAssetSoft);
        buttons.add(binding.btnRegisterAssetSexy);
        buttons.add(binding.btnRegisterAssetRomantic);

        for (int i = 0; i < buttons.size(); i++) {
            int finalI = i;
            Button button = buttons.get(finalI);
            button.setOnClickListener(v -> {
                selectedThemeId = themeIds[finalI];
                for (Button button2 : buttons) {
                    button2.setBackground(getDrawable(R.drawable.btn_gray));
                }
                button.setBackground(getDrawable(R.drawable.btn_pink));
            });
        }
    }

    public void btnToBack(View view) {
        onBackPressed();
    }

    public void btnToRegister(View view) {
        String name = binding.editRegisterAssetName.getText().toString();
        String description = binding.editRegisterAssetDescription.getText().toString();

        if (name.isEmpty() || description.isEmpty()) {
            Alert.makeText("에셋 이름/설명을 작성해주세요");
        }

        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), insertAssetRequest.getImg());
        MultipartBody.Part part = MultipartBody.Part.createFormData("img", insertAssetRequest.getImg().getName(), fileReqBody);

        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody landmarkBody = RequestBody.create(MediaType.parse("text/plain"), insertAssetRequest.getLandmark().name());
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), "3");
        RequestBody themeIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(selectedThemeId));

        ApiClient.getInstance().getAssetService()
                .insertAsset(part, descriptionBody, landmarkBody, nameBody, priceBody, themeIdBody)
                .enqueue(SimpleCallbackUtil.getSimpleCallback(apiMessage -> {
                    Alert.makeText("등록 성공");
                    finish();
                }));
    }
}
