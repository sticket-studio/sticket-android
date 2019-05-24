package com.sticket.app.sticket.activities.store.store_like;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sticket.app.sticket.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LikeAuthorItemAdapter extends BaseAdapter {
    private ArrayList<LikeAuthorItem> itemList = new ArrayList<LikeAuthorItem>();

    public LikeAuthorItemAdapter() {

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_author_like,parent,false);
        }
        ImageView img = (ImageView)convertView.findViewById(R.id.profile_image);
        TextView userName = (TextView)convertView.findViewById(R.id.userName);
        TextView workNum = (TextView)convertView.findViewById(R.id.workNum);
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView likeNum= (TextView)convertView.findViewById(R.id.likeNum);

        LikeAuthorItem likeAuthorItem = itemList.get(position);

        img.setImageDrawable(likeAuthorItem.getImg());
        userName.setText(likeAuthorItem.getUserName());
        workNum.setText(likeAuthorItem.getWorkNum());
        title.setText(likeAuthorItem.getTitle());
        likeNum.setText(likeAuthorItem.getLikeNum());


        //클릭하면 어쪌껀가~~~~~~ 여기다 정의하기
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public LikeAuthorItem getItem(int position){
        return itemList.get(position);
    }

    public void addItem(Drawable drawable, String userName, String workNum, String title, String likeNum){
        LikeAuthorItem likeAuthorItem = new LikeAuthorItem(drawable,userName,workNum,title,likeNum);
        itemList.add(likeAuthorItem);
    }

}
