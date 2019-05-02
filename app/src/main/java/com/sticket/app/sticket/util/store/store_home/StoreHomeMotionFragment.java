package com.sticket.app.sticket.util.store.store_home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;

public class StoreHomeMotionFragment extends Fragment {

    public StoreHomeMotionFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_motion, container, false);

        return view;
    }
}
