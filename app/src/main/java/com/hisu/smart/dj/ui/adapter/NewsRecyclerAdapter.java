package com.hisu.smart.dj.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.InformationEntity;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.HomeNewsHolder>{
    private List<InformationEntity> newsData;
    public NewsRecyclerAdapter(List<InformationEntity> list){
        newsData = list;
    }
    @Override
    public HomeNewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.home_news_recycler_item,parent,false);
        HomeNewsHolder newsHolder = new HomeNewsHolder(view);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(HomeNewsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }

    class HomeNewsHolder extends RecyclerView.ViewHolder{
        TextView newText;
        ImageView newImage;
        TextView dataText;
        TextView watchText;
        public HomeNewsHolder(View itemView) {
            super(itemView);
            newText = itemView.findViewById(R.id.show_news_message_textView);
            newImage = itemView.findViewById(R.id.show_news_imageView);
            dataText = itemView.findViewById(R.id.news_data_textView);
            watchText = itemView.findViewById(R.id.wactched_number_textView);
        }
    }

}
