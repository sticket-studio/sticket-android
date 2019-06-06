package com.sticket.app.sticket.activities.sticker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.sticket.app.sticket.R;

public class StickerGridFragment extends Fragment {

    private AdapterView.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sticker_grid, container, false);

        GridView stickerGridView = (GridView) view.findViewById(R.id.stickerGridView);
        StickerGridAdapter adapter = new StickerGridAdapter(getActivity());
        stickerGridView.setAdapter(adapter);

        stickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO : Set Asset or Sticker on your face
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
                Toast.makeText(getActivity(), "Clicked icon position:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
