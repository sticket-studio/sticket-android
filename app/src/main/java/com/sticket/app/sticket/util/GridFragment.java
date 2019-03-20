package com.sticket.app.sticket.util;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.sticket.app.sticket.R;


public class GridFragment extends Fragment{

    private GridView gridView;

    public GridFragment() {
        // Required empty public constructor
    }

//    // Q. Is it Redundant Code?
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        gridView = (GridView) view.findViewById(R.id.gridView);

        return view;
    }

}
