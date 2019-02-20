package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.irecyclerview.bean.PageBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.InformationEntity;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lichee
 */
public class StudyTopicAdapter extends RecyclerView.Adapter<StudyTopicAdapter.RankHolder> {

    private List<InformationEntity> dataList;
    private Context mContext;
    private PageBean pageBean;
    private OnTopicItemClickListener topicItemClickListener;

    public StudyTopicAdapter(Context context){
        mContext = context;
        dataList = new ArrayList<>();
        pageBean = new PageBean();
    }

    public void setData(List<InformationEntity> list){
        dataList.clear();
        dataList.addAll(list) ;
        notifyDataSetChanged();
    }

    public void addAll(List<InformationEntity> list){
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
    public void onBindViewHolder(RankHolder holder, final int position) {
        final InformationEntity topicEntity = dataList.get(position);
        holder.tv_title.setText(topicEntity.getName());
        holder.tv_date.setText(topicEntity.getPublishTime());
        int mediaType = topicEntity.getMediaType();
        if(mediaType == 0){
            holder.thematic_video.setVisibility(View.VISIBLE);
            holder.thematic_video.backButton.setVisibility(View.GONE);
            holder.thematic_video.tinyBackImageView.setVisibility(View.GONE);
            holder.thematic_video.startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(topicItemClickListener != null){
                        topicItemClickListener.onTopicClick(position,topicEntity);
                    }
                }
            });
            holder.thematic_video.thumbImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(topicItemClickListener != null){
                        topicItemClickListener.onTopicClick(position,topicEntity);
                    }
                }
            });
            holder.thematic_cover.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(topicEntity.getIcon())
                    .apply(new RequestOptions()
                            .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading))
                    .into(holder.thematic_video.thumbImageView);
        }else{
            holder.thematic_video.setVisibility(View.GONE);
            holder.thematic_cover.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(topicEntity.getIcon())
                    .apply(new RequestOptions()
                            .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading))
                    .into(holder.thematic_cover);
        }
        //设置点击事件
        if(topicItemClickListener!=null){
            holder.topic_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(topicItemClickListener != null){
                        topicItemClickListener.onTopicClick(position,topicEntity);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

     class RankHolder extends RecyclerView.ViewHolder{
        private ImageView thematic_cover;
        private JCVideoPlayerStandard thematic_video;
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_watch_num;
        private LinearLayout topic_layout;
        public RankHolder(View itemView) {
            super(itemView);
            thematic_cover = itemView.findViewById(R.id.thematic_cover);
            thematic_video = itemView.findViewById(R.id.thematic_video);
            tv_title = itemView.findViewById(R.id.thematic_video_title);
            tv_date = itemView.findViewById(R.id.thematic_date);
            tv_watch_num = itemView.findViewById(R.id.tv_thematic_number);
            topic_layout = itemView.findViewById(R.id.topic_item_LinearLayout);
        }
    }

    /**
     * 分页
     * @return
     */
    public PageBean getPageBean() {
        return pageBean;
    }

    public int getSize(){
        return dataList.size();
    }
    public interface  OnTopicItemClickListener{
        void onTopicClick(int position,InformationEntity data);
    }
    public void setOnItemClickListener(StudyTopicAdapter.OnTopicItemClickListener onItemClickListener){
        this.topicItemClickListener = onItemClickListener;
    }
}
