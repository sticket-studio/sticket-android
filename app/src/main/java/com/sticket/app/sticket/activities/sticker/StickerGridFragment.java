package com.sticket.app.sticket.activities.sticker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.util.Landmark;

public class StickerGridFragment extends Fragment {
    public static final int GRID_TYPE_ASSET_EYE = 1;
    public static final int GRID_TYPE_ASSET_NOSE = 2;
    public static final int GRID_TYPE_ASSET_MOUTH = 3;
    public static final int GRID_TYPE_ASSET_CHEEK = 4;
    public static final int GRID_TYPE_ASSET_EAR = 5;
    public static final int GRID_TYPE_STICON = 6;
    public static final int GRID_TYPE_MOTIONTICON = 7;
    public static final int[] GRID_TYPE_ARRAY = {GRID_TYPE_ASSET_EYE, GRID_TYPE_ASSET_NOSE
    , GRID_TYPE_ASSET_MOUTH, GRID_TYPE_ASSET_CHEEK, GRID_TYPE_ASSET_EAR
    , GRID_TYPE_STICON, GRID_TYPE_MOTIONTICON};

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

        if (getArguments() != null) {
            int type = getArguments().getInt("type");

            BaseAdapter adapter;

            if (type == GRID_TYPE_STICON) {
                adapter = new LiveSticonGridAdapter(getActivity());
            } else if (type == GRID_TYPE_MOTIONTICON) {
                adapter = new LiveMotionticonGridAdapter(getActivity());
            } else {
                Landmark landmark;
                if(type == GRID_TYPE_ASSET_EYE){
                    landmark = Landmark.EYE_LEFT;
                }else if(type == GRID_TYPE_ASSET_NOSE){
                    landmark = Landmark.NOSE;
                }else if(type == GRID_TYPE_ASSET_MOUTH){
                    landmark = Landmark.MOUTH;
                }else if(type == GRID_TYPE_ASSET_CHEEK){
                    landmark = Landmark.CHEEK_LEFT;
                }else{
                    landmark = Landmark.EAR_LEFT;
                }
                adapter = new LiveAssetGridAdapter(getActivity(), landmark);
            }

            stickerGridView.setAdapter(adapter);
            stickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO : Set Asset or Sticker on your face
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(parent, view, position, id);
                    }
                }
            });
        }

        stickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO : Set Asset or Sticker on your face
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });

        return view;
    }
}
