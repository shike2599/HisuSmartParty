package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.irecyclerview.bean.PageBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.InformationEntity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.LogUtils;

import java.io.File;
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
    private PageBean pageBean;
    public NewsRecyclerAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
        pageBean = new PageBean();
    }

    public void setData(List<InformationEntity> list){
        mDatas.clear();
        mDatas.addAll(list) ;
        Log.d("NewsRecyclerAdapter","mDatas.size()===="+mDatas.size());
        notifyDataSetChanged();
    }

    public void addAll(List<InformationEntity> list){
        mDatas.addAll(list) ;
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
                    newsItemClickListener.onNewsClick(position,mDatas.get(position));
                }
            });
        }
    }

    public  void convert(HomeNewsHolder holder, InformationEntity informationEntity){
        holder.newText.setText(informationEntity.getName());
        holder.dateText.setText(informationEntity.getPublishTime());
        String iconStr =  informationEntity.getIcon();
        Log.d("NewsRecyclerAdapter","icon--URL===" + informationEntity.getIcon());
        String urlStr = informationEntity.getUrl();
        int mediaType = informationEntity.getMediaType();
        if(mediaType == 0){
            holder.video.setVisibility(View.VISIBLE);
            holder.newImage.setVisibility(View.GONE);
            holder.video.backButton.setVisibility(View.GONE);
            holder.video.tinyBackImageView.setVisibility(View.GONE);
            holder.video.startButton.setEnabled(false);
            holder.video.thumbImageView.setEnabled(false);
            Glide.with(mContext)
                    .load(iconStr)
                    .apply(new RequestOptions()
                            .error(com.jaydenxiao.common.R.drawable.no_content_tip)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(com.jaydenxiao.common.R.drawable.no_content_tip))
                    .into(holder.video.thumbImageView);
        }else{
            holder.video.setVisibility(View.GONE);
            holder.newImage.setVisibility(View.VISIBLE);
            if(iconStr==""||iconStr==null||iconStr == AppConstant.HOST_URL){
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
        void onNewsClick(int position,InformationEntity data);
//        void onLongClick(int position);
    }
    public void setOnItemClickListener(NewsRecyclerAdapter.OnNewsItemClickListener onItemClickListener){
        this.newsItemClickListener = onItemClickListener;
    }

    /**
     * 分页
     * @return
     */
    public PageBean getPageBean() {
        return pageBean;
    }

    public int getSize(){
        return mDatas.size();
    }

}
