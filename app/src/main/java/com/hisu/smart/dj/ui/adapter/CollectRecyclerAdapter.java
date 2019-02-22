package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.hisu.smart.dj.entity.CollectEntity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lichee
 */
public class CollectRecyclerAdapter extends RecyclerView.Adapter<CollectRecyclerAdapter.CollectHolder>{
    private List<CollectEntity> mDatas;
    private Context mContext;
    private OnCollectItemClickListener collectItemClickListener;
    private PageBean pageBean;
    public CollectRecyclerAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
        pageBean = new PageBean();
    }

    public void setData(List<CollectEntity> list){
        mDatas.clear();
        mDatas.addAll(list) ;
        Log.d("NewsRecyclerAdapter","mDatas.size()===="+mDatas.size());
        notifyDataSetChanged();
    }

    public void addAll(List<CollectEntity> list){
        mDatas.addAll(list) ;
        notifyDataSetChanged();
    }

    @Override
    public CollectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_my_collect_layout,parent,false);
        CollectHolder newsHolder = new CollectHolder(view);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(CollectHolder holder,final int position) {
        convert(holder, mDatas.get(position));
        if(collectItemClickListener != null){
            holder.collectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collectItemClickListener.onCollectClick(position,mDatas.get(position));
                }
            });
        }
    }

    public  void convert(CollectHolder holder, CollectEntity collectEntity){
        holder.collectText.setText(collectEntity.getName());
        holder.dateText.setText(collectEntity.getCreateTime());

        String urlStr = collectEntity.getUrl();
        int mediaType = collectEntity.getMediaType();
        if(mediaType == 0){
            holder.video.setVisibility(View.VISIBLE);
            holder.collectImage.setVisibility(View.GONE);
            holder.video.backButton.setVisibility(View.GONE);
            holder.video.tinyBackImageView.setVisibility(View.GONE);
            holder.video.startButton.setEnabled(false);
            holder.video.thumbImageView.setEnabled(false);
            Glide.with(mContext)
                    .load(com.jaydenxiao.common.R.drawable.no_content_tip)
                    .apply(new RequestOptions()
                            .error(com.jaydenxiao.common.R.drawable.no_content_tip)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(com.jaydenxiao.common.R.drawable.no_content_tip))
                    .into(holder.video.thumbImageView);
        }else{
            holder.video.setVisibility(View.GONE);
            holder.collectImage.setVisibility(View.VISIBLE);
            ImageLoaderUtils.display(mContext,holder.collectImage,com.jaydenxiao.common.R.drawable.no_content_tip);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class CollectHolder extends RecyclerView.ViewHolder{
        TextView collectText;
        ImageView collectImage;
        TextView dateText;
        JCVideoPlayerStandard video;
        LinearLayout collectLayout;
        public CollectHolder(View itemView) {
            super(itemView);
            collectText = itemView.findViewById(R.id.my_collect_text);
            collectImage = itemView.findViewById(R.id.my_collect_image);
            dateText = itemView.findViewById(R.id.my_collect_date);
            video = itemView.findViewById(R.id.my_collect_video);
            collectLayout = itemView.findViewById(R.id.my_collect_layout);
        }
    }

    public interface  OnCollectItemClickListener{
        void onCollectClick(int position, CollectEntity data);
    }
    public void setOnItemClickListener(CollectRecyclerAdapter.OnCollectItemClickListener onItemClickListener){
        this.collectItemClickListener = onItemClickListener;
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
