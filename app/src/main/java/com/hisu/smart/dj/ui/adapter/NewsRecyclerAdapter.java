package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.InformationEntity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lichee
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.HomeNewsHolder>{
    private List<InformationEntity> mDatas;
    private Context mContext;
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
    public void onBindViewHolder(HomeNewsHolder holder, int position) {
        convert(holder, mDatas.get(position));
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
            holder.video.setUp(urlStr, JCVideoPlayer.SCREEN_LAYOUT_LIST);
        }else{
            holder.video.setVisibility(View.GONE);
            holder.newImage.setVisibility(View.VISIBLE);
            if(!"".equals(iconStr)){
                ImageLoaderUtils.display(mContext,holder.newImage,R.mipmap.icon_news_cover);
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
        public HomeNewsHolder(View itemView) {
            super(itemView);
            newText = itemView.findViewById(R.id.show_news_message_textView);
            newImage = itemView.findViewById(R.id.show_news_imageView);
            dateText = itemView.findViewById(R.id.news_date_textView);
            watchText = itemView.findViewById(R.id.wactched_number_textView);
            video = itemView.findViewById(R.id.watch_video);
        }
    }


}
