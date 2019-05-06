package com.sticket.app.sticket.util.store.store_home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;

import java.util.ArrayList;
import java.util.stream.Stream;

public class StoreHomeHomeFragment extends Fragment {

    public StoreHomeHomeFragment(){
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_start, container, false);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<4; i++) {
            list.add("귀염뽀짝");
            list.add("섹시도발");
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = view.findViewById(R.id.today_item_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));     // fragment이므로 this -> getActivity()

        // 리사이클러뷰에 ItemAdapter 객체 지정.
        ItemAdapter adapter = new ItemAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
