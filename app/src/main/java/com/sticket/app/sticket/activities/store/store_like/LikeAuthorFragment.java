package com.sticket.app.sticket.activities.store.store_like;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sticket.app.sticket.R;



public class LikeAuthorFragment extends Fragment {
    private ListView listView;
    private LikeAuthorItemAdapter adapter;

    public LikeAuthorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_like_author, container, false);
        adapter = new LikeAuthorItemAdapter();
        listView= (ListView)view.findViewById(R.id.authorItemListView);
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getContext(),R.drawable.yeoni),"yeoni","13","칭호 뭘로하지?","111");

        return view;
    }

}
