package com.sticket.app.sticket.activities.store.store_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.common.api.Api;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.store.store_home.StoreHomeStickerGridAdapter;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.dao.AssetDao;
import com.sticket.app.sticket.database.dao.MotionticonDao;
import com.sticket.app.sticket.database.dao.Motionticon_sticonDao;
import com.sticket.app.sticket.database.dao.SticonDao;
import com.sticket.app.sticket.database.dao.Sticon_assetDao;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.models.User;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;
import com.sticket.app.sticket.retrofit.dto.response.user.UserPageResponse;
import com.sticket.app.sticket.util.FileUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StoreRegisterViewFragment extends Fragment {
    String landStr;
    String landmark;
    public StoreRegisterViewFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sticker, container, false);

        GridView storeStickerGridView = (GridView) view.findViewById(R.id.storeStickerGridView);
        StoreHomeStickerGridAdapter adapter = new StoreHomeStickerGridAdapter(getActivity());
        storeStickerGridView.setAdapter(adapter);
        landStr = getArguments().getString("landmark");
        Log.i("프래그먼트",landStr);
        landmark ="EYE_LEFT";
        if(landStr.equals("눈")){
            landmark  = "EYE_LEFT";
        }else if(landStr.equals("코")){
            landmark ="NOSE";
        }else if(landStr.equals("입")) {
            landmark  = "MOUTH";
        }else if(landStr.equals("볼")){
            landmark  = "CHEEK_LEFT";
        }else if(landStr.equals("귀")){
            landmark  = "EAR_LEFT";
        }
        ApiClient.getInstance().getUserService().getMyInfo()
                .enqueue(new CustomCallback<UserPageResponse>() {
                    @Override
                    public void onResponse(Call<UserPageResponse > call, Response<UserPageResponse > response) {
                        if (response.body() == null) {
                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
                        } else {
                            if(landStr.equals("눈")){
                                landStr="왼눈";
                            }
                            UserPageResponse userInfo = response.body();
                            SticketDatabase sd = SticketDatabase.getDatabase(getContext());
                            List<Asset> allAsset = sd.assetDao().getAllassets();

                            for (Asset as : allAsset){
                                Log.i("idx test",as.getIdx()+"");
                                if(as.getIdx()>28 &&as.getLandmark().getKorName().equals(landStr)){
                                    Log.i("imgUrl test", as.getLocalUrl());
                                    SimpleAssetResponse sar = new SimpleAssetResponse(as.getIdx(),as.getLocalUrl(),userInfo.getName(),landStr,ApiClient.getInstance().getUserId(), userInfo.getName(),0,"",0);
                                    adapter.addItem(sar);
                                }
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                });




        /*
        ApiClient.getInstance().getAssetService()
                .searchAssets(ApiClient.getInstance().getUserId(), 0,landmark,0)
                .enqueue(new CustomCallback<List<SimpleAssetResponse>>() {
                    @Override
                    public void onResponse(Call<List<SimpleAssetResponse>> call, Response<List<SimpleAssetResponse>> response) {
                        if (response.body() == null) {
                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
                        } else {
                            for(SimpleAssetResponse sa : response.body()){
                                Log.i("name",sa.getName());
                                adapter.addItem(sa);
                            }
                            adapter.notifyDataSetChanged();
                            Log.i("h",adapter.getCount()+"");
                        }
                    }
                });

         */

        storeStickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO : Register Page
                Intent intent = new Intent(getActivity(), StoreRegisterAssetActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
