package com.sticket.app.sticket.activities.store.store_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.store.store_home.StoreHomeStickerGridAdapter;

public class StoreRegisterViewFragment extends Fragment {

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