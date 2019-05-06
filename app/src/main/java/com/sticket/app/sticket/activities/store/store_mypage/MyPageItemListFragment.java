package com.sticket.app.sticket.activities.store.store_mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.util.ItemAdapter;

import java.util.ArrayList;

public class MyPageItemListFragment extends Fragment {

    public MyPageItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypage_viewpager, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));     // fragment이므로 this -> getActivity()

        // Create dummy data displayed in RecyclerView
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Log.e("aa","aaaa");
            list.add("귀염뽀짝");
            list.add("섹시도발");
        }

        ItemAdapter itemAdapter = new ItemAdapter(list);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }
}
