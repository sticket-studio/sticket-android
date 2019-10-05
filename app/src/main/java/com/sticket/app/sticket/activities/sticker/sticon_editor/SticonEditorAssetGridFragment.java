package com.sticket.app.sticket.activities.sticker.sticon_editor;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.adapter.SticonEditorGridAdapter;
import com.sticket.app.sticket.adapter.SticonEditorGridAdapter.OnAssetClickListener;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.databinding.FragmentStickerGrid2Binding;

import java.util.List;

public class SticonEditorAssetGridFragment extends Fragment {
    private List<Asset> assetList;
    private FragmentStickerGrid2Binding binding;

    private OnAssetClickListener onAssetClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sticker_grid2, container, false);

        SticketDatabase sticketDatabase = SticketDatabase.getDatabase(getContext());
        assetList = sticketDatabase.assetDao().getAllassets();

        SticonEditorGridAdapter adapter = new SticonEditorGridAdapter(assetList);
        adapter.setOnAssetClickListener(onAssetClickListener);
        binding.recyclerAssetGrid.setLayoutManager(new GridLayoutManager(getContext(), 5));
        binding.recyclerAssetGrid.setAdapter(adapter);

        return binding.getRoot();
    }

    public void setOnAssetClickListener(OnAssetClickListener onAssetClickListener) {
        this.onAssetClickListener = onAssetClickListener;
    }
}
