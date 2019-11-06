package com.sticket.app.sticket.activities.store.store_like;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.store.store_mypage.StoreMyPageFragment;
import com.sticket.app.sticket.databinding.ActivityStoreBinding;
import com.sticket.app.sticket.models.Asset;
import com.sticket.app.sticket.models.User;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.ApiConfig;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.client.MyInterceptor;
import com.sticket.app.sticket.retrofit.client.service.UserService;
import com.sticket.app.sticket.retrofit.dto.response.user.UserPageResponse;
import com.sticket.app.sticket.retrofit.dto.response.user.UserSimple;
import com.sticket.app.sticket.util.SimpleCallbackUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LikeAuthorFragment extends Fragment {

    private ListView likeAuthorListView;
    private LikeAuthorItemAdapter likeAuthorAdapter;
    List<UserSimple> userSimples;
    public LikeAuthorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_like_author, container, false);
        likeAuthorAdapter = new LikeAuthorItemAdapter();
        likeAuthorAdapter.setOnAuthorClickListener(userId->{
            ActivityStoreBinding activityStoreBinding
                    = DataBindingUtil.setContentView(getActivity(), R.layout.activity_store);
            StoreMyPageFragment storeMyPageFragment = new StoreMyPageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(StoreMyPageFragment.EXTRA_USER_IDX, userId);
            storeMyPageFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container, storeMyPageFragment).commit();
            activityStoreBinding.txtToolbarTitle.setText("작가페이지");
            activityStoreBinding.drawerLayout.closeDrawer(GravityCompat.START);
        });
        likeAuthorListView = (ListView) view.findViewById(R.id.layout_authorItemView_listView);
        likeAuthorListView.setAdapter(likeAuthorAdapter);

        ApiClient.getInstance().getUserService()
                .getMyLikeAuthor()
                .enqueue(new CustomCallback<List<UserSimple>>() {
            @Override
            public void onResponse(Call<List<UserSimple>> call, Response<List<UserSimple>> response) {
                if (response.body() == null) {
                    Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
                } else {

                    for(UserSimple us : response.body()){
                        likeAuthorAdapter.addItem(us.getId(), us.getImgUrl(),us.getName(),us.getWorksCnt(),us.getDescription(),us.getFollowerCnt()+"");
                    }
                    likeAuthorAdapter.notifyDataSetChanged();
                }
            }
        });

        /*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        UserService us = ApiClient.getInstance().getUserService();
        Call<List<UserSimple>> call = us.getMyLikeAuthor();
        try {
            List<UserSimple> result = call.execute().body();
            for(UserSimple u : result){
                likeAuthorAdapter.addItem(u.getImgUrl(),u.getName(),u.getWorksCnt(),u.getDescription(),u.getFollowerCnt()+"");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        */

        return view;
    }
}

