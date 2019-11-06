package com.sticket.app.sticket.activities.store.store_like;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.StoreHomeHomeAssetsAdapter;
import com.sticket.app.sticket.databinding.FragmentLikeAssetBinding;
import com.sticket.app.sticket.databinding.FragmentStoreStartBinding;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class LikeAssetFragment extends Fragment {

    public LikeAssetFragment() {
    }

    private FragmentLikeAssetBinding binding;
    private List<SimpleAssetResponse> eyeAssets = new ArrayList<>();
    private List<SimpleAssetResponse> noseAssets = new ArrayList<>();
    private List<SimpleAssetResponse> mouseAssets = new ArrayList<>();
    private List<SimpleAssetResponse> cheekAssets = new ArrayList<>();
    private List<SimpleAssetResponse> earAssets = new ArrayList<>();


    private StoreHomeHomeAssetsAdapter eyeAssetsAdapter;
    private StoreHomeHomeAssetsAdapter noseAssetsAdapter;
    private StoreHomeHomeAssetsAdapter mouseAssetsAdapter;
    private StoreHomeHomeAssetsAdapter cheekAssetsAdapter;
    private StoreHomeHomeAssetsAdapter earAssetsAdpater;

    private int eyePage =1;
    private int nosePage =1;
    private int mousePage =1;
    private int cheekPage =1;
    private int earPage =1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eyeAssetsAdapter =  new StoreHomeHomeAssetsAdapter(eyeAssets);
        noseAssetsAdapter = new StoreHomeHomeAssetsAdapter(noseAssets);
        mouseAssetsAdapter = new StoreHomeHomeAssetsAdapter(mouseAssets);
        cheekAssetsAdapter = new StoreHomeHomeAssetsAdapter(cheekAssets);
        earAssetsAdpater = new StoreHomeHomeAssetsAdapter(earAssets);

        dataBinding();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_like_asset, container, false);

        // Set RecyclerView to StoreMyPageAssetAdapter
        //TODO: 오른쪽 끝까지 가면 새로운 페이지 가져오는 기능 구현

        binding.eyeItemRecycler.setAdapter(eyeAssetsAdapter);
        binding.noseItemRecycler.setAdapter(noseAssetsAdapter);
        binding.mouseItemRecycler.setAdapter(mouseAssetsAdapter);
        binding.cheekItemRecycler.setAdapter(cheekAssetsAdapter);
        binding.earItemRecycler.setAdapter(earAssetsAdpater);

        return binding.getRoot();
    }

    public void dataBinding(){
        ApiClient.getInstance()
                .getAssetService()
                .getMyLikeAssets()
                .enqueue(new CustomCallback<List<SimpleAssetResponse>>() {
                    @Override
                    public void onResponse(Call<List<SimpleAssetResponse>> call, Response<List<SimpleAssetResponse>> response) {
                        if (response.body() == null) {
                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
                        } else {
                            for(SimpleAssetResponse sa : response.body()){
                                String landStr = sa.getLandmark();
                                if(landStr.equals("EYE_LEFT")){
                                    eyeAssets.add(sa);
                                }else if(landStr.equals("NOSE")){
                                    noseAssets.add(sa);
                                }else if(landStr.equals("MOUTH_BOTTOM")) {
                                    mouseAssets.add(sa);
                                }else if(landStr.equals("CHEEK_LEFT")){
                                    cheekAssets.add(sa);
                                }else if(landStr.equals("EAR_LEFT")){
                                    earAssets.add(sa);
                                }
                            }
                            eyeAssetsAdapter.notifyDataSetChanged();
                            noseAssetsAdapter.notifyDataSetChanged();
                            mouseAssetsAdapter.notifyDataSetChanged();
                            cheekAssetsAdapter.notifyDataSetChanged();
                            earAssetsAdpater.notifyDataSetChanged();
                        }
                    }
                });
    }



}
