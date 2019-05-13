package com.sticket.app.sticket.activities.store.store_like;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;

public class LikeSticonFragment extends Fragment {
    public LikeSticonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_like_sticon, container, false);

        return view;
    }
}
