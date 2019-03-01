package com.hisu.smart.dj.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.irecyclerview.bean.PageBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.StudyListEntity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lichee
 */
public class StudyListRecyclerAdapter extends RecyclerView.Adapter<StudyListRecyclerAdapter.StudyListHolder>{
    private List<StudyListEntity.DataListBean> mDatas;
    private Context mContext;
    private OnStudyItemClickListener studyItemClickListener;
    private PageBean pageBean;
    public StudyListRecyclerAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
        pageBean = new PageBean();
    }

    public void setData(List<StudyListEntity.DataListBean> list){
        mDatas.clear();
        mDatas.addAll(list) ;
        Log.d("NoticeRecyclerAdapter","mDatas.size()===="+mDatas.size());
        notifyDataSetChanged();
    }

    public void addAll(List<StudyListEntity.DataListBean> list){
        mDatas.addAll(list) ;
        notifyDataSetChanged();
    }

    @Override
    public StudyListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.home_news_recycler_item,parent,false);
        StudyListHolder studyListHoldr = new StudyListHolder(view);
        return studyListHoldr;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(StudyListHolder holder,final int position) {
        StudyListEntity.DataListBean notiveBean = mDatas.get(position);
        Log.d("NoticeRecyclerAdapter","mDatas--size()==="+mDatas.size());
        Log.d("NoticeRecyclerAdapter","notiveBean--==="+notiveBean.getName());
        convert(holder,notiveBean);
        if(studyItemClickListener!=null){
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    studyItemClickListener.onStudyItemClick(position,mDatas.get(position));
                }
            });
        }
    }

    public  void convert(StudyListHolder holder, StudyListEntity.DataListBean studyBean){
        holder.watchText.setVisibility(View.GONE);
        holder.watchImage.setVisibility(View.GONE);
        holder.newText.setText(studyBean.getName());
        holder.dateText.setText(studyBean.getPublishTime());
        String iconStr = "";
        if(studyBean.getImages()!=null&&studyBean.getImages().size()>0){
             iconStr =  (String)studyBean.getImages().get(0);//拿到第一个图片路径
        }
        String urlStr = studyBean.getUrl();
        int mediaType = studyBean.getMediaType();
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

    class StudyListHolder extends RecyclerView.ViewHolder{
        TextView newText;
        ImageView newImage;
        ImageView watchImage;
        TextView dateText;
        TextView watchText;
        JCVideoPlayerStandard video;
        RelativeLayout relativeLayout;
        public StudyListHolder(View itemView) {
            super(itemView);
            newText = itemView.findViewById(R.id.show_news_message_textView);
            newImage = itemView.findViewById(R.id.show_news_imageView);
            watchImage = itemView.findViewById(R.id.watch_imageview);
            dateText = itemView.findViewById(R.id.news_date_textView);
            watchText = itemView.findViewById(R.id.wactched_number_textView);
            video = itemView.findViewById(R.id.watch_video);
            relativeLayout = itemView.findViewById(R.id.news_item_relativeLayout);
        }
    }

    public interface  OnStudyItemClickListener{
        void onStudyItemClick(int position, StudyListEntity.DataListBean studyBean);
    }
    public void setOnItemClickListener(StudyListRecyclerAdapter.OnStudyItemClickListener onItemClickListener){
        this.studyItemClickListener = onItemClickListener;
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
