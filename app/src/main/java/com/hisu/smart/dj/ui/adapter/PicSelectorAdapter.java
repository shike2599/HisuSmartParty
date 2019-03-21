package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hisu.smart.dj.R;

import java.io.File;
import java.util.ArrayList;

public class PicSelectorAdapter extends RecyclerView.Adapter<PicSelectorAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mImages;
    private LayoutInflater mInflater;
    private OnImgItemClickListener imgItemClickListener;
    public PicSelectorAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.pic_selector_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String image = mImages.get(position);
        Glide.with(mContext).load(new File(image)).into(holder.ivImage);
        //设置点击事件
        if(imgItemClickListener!=null){
            holder.del_select_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgItemClickListener.onImgItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void refresh(ArrayList<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView del_select_img;
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            del_select_img = itemView.findViewById(R.id.unSelected_ImageView);
        }
    }

    public interface  OnImgItemClickListener{
        void onImgItemClick(int position);
    }
    public void setOnItemClickListener(OnImgItemClickListener onItemClickListener){
        this.imgItemClickListener = onItemClickListener;
    }
}
