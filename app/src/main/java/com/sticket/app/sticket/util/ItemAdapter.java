package com.sticket.app.sticket.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sticket.app.sticket.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mData = null;
    private ImageView itemImage;
    private TextView itemThemeText;


    // ViewHolder Class to store ItemView
    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);

            // Reference for View Object (hold strong reference)
            itemImage = itemView.findViewById(R.id.item_image);
            itemThemeText = itemView.findViewById(R.id.item_theme_text);
        }
    }

    // Get Data list in Constructor
    public ItemAdapter(ArrayList<String> list) {
        mData = list;
    }

    // Create & Return ViewHolder Object for ItemView
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_store, parent, false);
        ItemAdapter.ViewHolder vh = new ItemAdapter.ViewHolder(view);

        return vh;
    }

    // Display data corresponding to the position in the ItemView of the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String text = mData.get(position);
        itemThemeText.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
