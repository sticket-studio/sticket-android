package com.sticket.app.sticket.activities.store.store_like;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sticket.app.sticket.R;



public class LikeAuthorFragment extends Fragment {
    private ListView likeAuthorListView;
    private LikeAuthorItemAdapter likeAuthorAdapter;

    public LikeAuthorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_like_author, container, false);
        likeAuthorAdapter = new LikeAuthorItemAdapter();
        likeAuthorListView = (ListView)view.findViewById(R.id.layout_authorItemView_listView);
        likeAuthorListView.setAdapter(likeAuthorAdapter);

        likeAuthorAdapter.addItem(R.drawable.yeoni,"yeoni",13,"칭호 뭘로하지?","111");

        return view;
    }

}
