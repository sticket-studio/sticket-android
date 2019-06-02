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
    private class LikeAuthorItemViewHolder {
        public LikeAuthorItemViewHolder(ImageView userImge, TextView userName, TextView workCount, TextView title, TextView likeNum) {
            this.userImge = userImge;
            this.userName = userName;
            this.workCount = workCount;
            this.title = title;
            this.likeNum = likeNum;
        }

        ImageView userImge;
        TextView userName;
        TextView workCount;
        TextView title;
        TextView likeNum;
    }

    private ArrayList<LikeAuthorItem> itemList = new ArrayList<LikeAuthorItem>();

    public LikeAuthorItemAdapter() {

    }

    @Override
    public int getCount() {
        return this.itemList.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        int pos = position;
        LikeAuthorItemViewHolder likeAuthorItemViewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_author_like,parent,false);
            ImageView userImg = (ImageView)convertView.findViewById(R.id.img_like_author_userImg);
            TextView userName = (TextView)convertView.findViewById(R.id.txt_like_author_userName);
            TextView workCount = (TextView)convertView.findViewById(R.id.txt_like_author_workCount);
            TextView title = (TextView)convertView.findViewById(R.id.txt_like_author_title);
            TextView likeNum= (TextView)convertView.findViewById(R.id.txt_like_author_likeNum);
            likeAuthorItemViewHolder = new LikeAuthorItemViewHolder(userImg,userName,workCount,title,likeNum);
        }else{
            likeAuthorItemViewHolder= (LikeAuthorItemViewHolder)convertView.getTag();
        }


        LikeAuthorItem likeAuthorItem = itemList.get(position);

        likeAuthorItemViewHolder.userImge.setImageResource(likeAuthorItem.getUserImg());
        likeAuthorItemViewHolder.userName.setText(likeAuthorItem.getUserName());
        likeAuthorItemViewHolder.workCount.setText(String.valueOf(likeAuthorItem.getWorkCount()));
        likeAuthorItemViewHolder.title.setText(likeAuthorItem.getTitle());
        likeAuthorItemViewHolder.likeNum.setText(likeAuthorItem.getLikeNum());


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

    public void addItem(int resourse, String userName, int workCount, String title, String likeNum){
        LikeAuthorItem likeAuthorItem = new LikeAuthorItem(resourse,userName,workCount ,title,likeNum);
        itemList.add(likeAuthorItem);
    }



}
