package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.InformationEntity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lichee
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.HomeNewsHolder>{
    private List<InformationEntity> mDatas;
    private Context mContext;
    private OnNewsItemClickListener newsItemClickListener;
    public NewsRecyclerAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    public void setData(List<InformationEntity> list){
        mDatas = list;
        notifyDataSetChanged();
    }
    @Override
    public HomeNewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.home_news_recycler_item,parent,false);
        HomeNewsHolder newsHolder = new HomeNewsHolder(view);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(HomeNewsHolder holder,final int position) {
        convert(holder, mDatas.get(position));
        if(newsItemClickListener!=null){
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsItemClickListener.onNewsClick(position);
                }
            });
        }
    }

    public  void convert(HomeNewsHolder holder, InformationEntity informationEntity){
        holder.newText.setText(informationEntity.getName());
        holder.dateText.setText(informationEntity.getPublishTime());
        String iconStr =  informationEntity.getIcon();
        String urlStr = informationEntity.getUrl();
        int mediaType = informationEntity.getMediaType();
        if(mediaType == 0){
            holder.video.setVisibility(View.VISIBLE);
            holder.newImage.setVisibility(View.GONE);
            holder.video.backButton.setVisibility(View.GONE);
            holder.video.tinyBackImageView.setVisibility(View.GONE);
            holder.video.startButton.setEnabled(false);
            Glide.with(mContext).load(iconStr)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                    .crossFade().into(holder.video.thumbImageView);
        }else{
            holder.video.setVisibility(View.GONE);
            holder.newImage.setVisibility(View.VISIBLE);
            if(!"".equals(iconStr)){
                ImageLoaderUtils.display(mContext,holder.newImage,R.mipmap.news_cover_icon);
            }else{
                ImageLoaderUtils.display(mContext,holder.newImage,iconStr);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class HomeNewsHolder extends RecyclerView.ViewHolder{
        TextView newText;
        ImageView newImage;
        TextView dateText;
        TextView watchText;
        JCVideoPlayerStandard video;
        RelativeLayout relativeLayout;
        public HomeNewsHolder(View itemView) {
            super(itemView);
            newText = itemView.findViewById(R.id.show_news_message_textView);
            newImage = itemView.findViewById(R.id.show_news_imageView);
            dateText = itemView.findViewById(R.id.news_date_textView);
            watchText = itemView.findViewById(R.id.wactched_number_textView);
            video = itemView.findViewById(R.id.watch_video);
            relativeLayout = itemView.findViewById(R.id.news_item_relativeLayout);
        }
    }

    public interface  OnNewsItemClickListener{
        void onNewsClick(int position);
//        void onLongClick(int position);
    }
    public void setOnItemClickListener(NewsRecyclerAdapter.OnNewsItemClickListener onItemClickListener){
        this.newsItemClickListener = onItemClickListener;
    }

}
