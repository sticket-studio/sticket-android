package com.sticket.app.sticket.activities.store.store_viewbyasset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.store.store_home.StoreHomeStickerGridAdapter;
import com.sticket.app.sticket.activities.store.store_preview.StorePreviewActivity;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.ApiConfig;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;
import com.sticket.app.sticket.retrofit.dto.response.user.UserSimple;
import com.sticket.app.sticket.util.Landmark;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StoreViewByAssetViewFragment extends Fragment {

    public StoreViewByAssetViewFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sticker, container, false);
        StoreHomeStickerGridAdapter adapter = new StoreHomeStickerGridAdapter(getContext());
        GridView storeStickerGridView = (GridView) view.findViewById(R.id.storeStickerGridView);
        String landStr = getArguments().getString("landmark");

        storeStickerGridView.setAdapter(adapter);
        String landmark ="EYE_LEFT";
        if(landStr.equals("왼눈")){
            landmark = "EYE_LEFT";
        }else if(landStr.equals("코")){
            landmark="NOSE";
        }else if(landStr.equals("입")) {
            landmark = "MOUTH_BOTTOM";
        }else if(landStr.equals("볼")){
            landmark = "CHEEK_LEFT";
        }else if(landStr.equals("귀")){
            landmark = "EAR_LEFT";
        }
        ApiClient.getInstance().getAssetService()
                .searchAssets(0, 0,landmark,0)
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

        storeStickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO : Preview Page
                Intent intent = new Intent(getActivity(), StorePreviewActivity.class);
                Log.i("assetName",adapter.getItem(position).getName());
                intent.putExtra("assetName",adapter.getItem(position).getName());
                intent.putExtra("assetId",adapter.getItem(position).getId()+"");
                startActivity(intent);
            }
        });
        return view;
    }
}
