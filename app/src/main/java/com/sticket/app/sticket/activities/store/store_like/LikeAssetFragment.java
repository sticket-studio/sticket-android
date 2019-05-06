package com.sticket.app.sticket.activities.store.store_like;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;

public class LikeAssetFragment extends Fragment {

    public LikeAssetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_like_asset, container, false);

        return view;
    }
}
