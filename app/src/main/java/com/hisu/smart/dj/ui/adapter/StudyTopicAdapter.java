package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.TopicPlanEntity;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class StudyTopicAdapter extends RecyclerView.Adapter<StudyTopicAdapter.RankHolder> {
    private List<TopicPlanEntity> dataList;
    private Context mContext;
    public StudyTopicAdapter(Context context){
        mContext = context;
        dataList = new ArrayList<>();
    }
    public void setData(List<TopicPlanEntity> list){
        dataList.clear();
        dataList.addAll(list) ;
        notifyDataSetChanged();
    }
     @Override
    public RankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thematic_layout,parent,false);
        RankHolder holder = new RankHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankHolder holder, int position) {
        TopicPlanEntity topicEntity = dataList.get(position);
        holder.tv_title.setText(topicEntity.getName());
        holder.tv_date.setText(topicEntity.getPublishTime());
        holder.thematic_video.backButton.setVisibility(View.GONE);
        holder.thematic_video.tinyBackImageView.setVisibility(View.GONE);
        holder.thematic_video.startButton.setEnabled(false);
        holder.thematic_video.thumbImageView.setEnabled(false);
        Glide.with(mContext)
                .load(topicEntity.getIcon())
                .apply(new RequestOptions()
                        .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_launcher))
                .into(holder.thematic_video.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

     class RankHolder extends RecyclerView.ViewHolder{
        private JCVideoPlayerStandard thematic_video;
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_watch_num;
        public RankHolder(View itemView) {
            super(itemView);
            thematic_video = itemView.findViewById(R.id.thematic_video);
            tv_title = itemView.findViewById(R.id.thematic_video_title);
            tv_date = itemView.findViewById(R.id.thematic_date);
            tv_watch_num = itemView.findViewById(R.id.tv_thematic_number);
        }
    }
}
