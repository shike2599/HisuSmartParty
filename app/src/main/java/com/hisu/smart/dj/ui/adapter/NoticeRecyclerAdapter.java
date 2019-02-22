package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
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
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lichee
 */
public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.NoticeHolder>{
    private List<NoticeInfoEntity.DataListBean> mDatas;
    private Context mContext;
    private OnNoticeItemClickListener noticeItemClickListener;
    private PageBean pageBean;
    public NoticeRecyclerAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
        pageBean = new PageBean();
    }

    public void setData(List<NoticeInfoEntity.DataListBean> list){
        mDatas.clear();
        mDatas.addAll(list) ;
        Log.d("NoticeRecyclerAdapter","mDatas.size()===="+mDatas.size());
        notifyDataSetChanged();
    }

    public void addAll(List<NoticeInfoEntity.DataListBean> list){
        mDatas.addAll(list) ;
        notifyDataSetChanged();
    }

    @Override
    public NoticeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.notice_recycler_item,parent,false);
        NoticeHolder newsHolder = new NoticeHolder(view);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(NoticeHolder holder,final int position) {
        NoticeInfoEntity.DataListBean notiveBean = mDatas.get(position);
        Log.d("NoticeRecyclerAdapter","mDatas--size()==="+mDatas.size());
        Log.d("NoticeRecyclerAdapter","notiveBean--==="+notiveBean.getName());
        holder.nameTv.setText(notiveBean.getName());
        holder.contentTv.setText(notiveBean.getContent());
        holder.timeTv.setText(notiveBean.getPublishTime());
        if(notiveBean.getName().equals("系统消息")){
            if(notiveBean.isHasRead()){
                holder.noticeImg.setBackgroundResource(R.mipmap.system_meg_open_icon);
            }else{
                holder.noticeImg.setBackgroundResource(R.mipmap.system_meg_noopen_icon);
            }
        }else{
            if(notiveBean.isHasRead()){
                holder.noticeImg.setBackgroundResource(R.mipmap.meg_opened_icon);
            }else{
                holder.noticeImg.setBackgroundResource(R.mipmap.meg_noopened_icon);
            }
        }

        if(noticeItemClickListener!=null){
            holder.noticeRelaLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noticeItemClickListener.onNoticeClick(position,mDatas.get(position));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class NoticeHolder extends RecyclerView.ViewHolder{
        ImageView noticeImg;
        TextView nameTv;
        TextView contentTv;
        TextView timeTv;
        RelativeLayout noticeRelaLayout;
        public NoticeHolder(View itemView) {
            super(itemView);
            noticeImg = itemView.findViewById(R.id.notice_imageView);
            nameTv = itemView.findViewById(R.id.notice_name);
            contentTv = itemView.findViewById(R.id.notice_content);
            timeTv = itemView.findViewById(R.id.notice_time);
            noticeRelaLayout = itemView.findViewById(R.id.notice_item_relativeLayout);
        }
    }

    public interface  OnNoticeItemClickListener{
        void onNoticeClick(int position,NoticeInfoEntity.DataListBean noticeBean);
    }
    public void setOnItemClickListener(NoticeRecyclerAdapter.OnNoticeItemClickListener onItemClickListener){
        this.noticeItemClickListener = onItemClickListener;
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
