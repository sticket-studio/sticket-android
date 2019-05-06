package com.sticket.app.sticket.util.store.store_home;

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
    ImageView itemImage;
    TextView itemThemeText;


    // ViewHolder Class to store ItemView
    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            itemImage = itemView.findViewById(R.id.item_image);
            itemThemeText = itemView.findViewById(R.id.item_theme_text);
        }
    }

    // Get Data list in Constructor
    ItemAdapter(ArrayList<String> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemAdapter.ViewHolder vh = new ItemAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
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
