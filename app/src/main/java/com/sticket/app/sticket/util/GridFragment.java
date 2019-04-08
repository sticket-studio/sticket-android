package com.sticket.app.sticket.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.sticket.app.sticket.R;

public class GridFragment extends Fragment{

    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(getActivity());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO : Set Asset or Sticker on your face
                Toast.makeText(getActivity(), "Clicked icon position:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
