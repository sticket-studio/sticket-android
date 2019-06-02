package com.sticket.app.sticket.activities.sticker.asset_importer;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sticket.app.sticket.R;

import java.util.ArrayList;

public class AssetImporterAdapter extends RecyclerView.Adapter<AssetImporterAdapter.ViewHolder> {

    private ArrayList<Uri> galleryUriList = null;
    private   ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView galleryImg;

        ViewHolder(View itemView){
            super(itemView);
            galleryImg = itemView.findViewById(R.id.img_asset_importer);
        }

    }

    AssetImporterAdapter(ArrayList<Uri> galleryList){
        this.galleryUriList = galleryList;
    }

    @Override
    public AssetImporterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_asset_importer_gallery,parent,false);
        AssetImporterAdapter.ViewHolder vh = new AssetImporterAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AssetImporterAdapter.ViewHolder viewHolder, final int position) {
            Uri uri = galleryUriList.get(position);
            viewHolder.galleryImg.setImageURI(uri);
            viewHolder.galleryImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(v, position);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return this.galleryUriList.size();
    }
}
