package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.HomeItemBean;

import java.util.List;

public class HomeReaycleAdapter extends RecyclerView.Adapter<HomeReaycleAdapter.HomeRecycleHolder> {
    private List<HomeItemBean> dataList;
    private OnItemClickListener onItemClickListener;

    public HomeReaycleAdapter(List<HomeItemBean> list){
        dataList = list;
    }

    @Override
    public HomeRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_recycleview_item,parent,false);
        HomeRecycleHolder holder = new HomeRecycleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeRecycleHolder holder, final int position) {
        HomeItemBean bean = dataList.get(position);

        holder.imageView.setImageResource(bean.getItemImage());
        holder.textView.setText(bean.getItemName());

        if(onItemClickListener!=null){
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class HomeRecycleHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        LinearLayout itemLayout;
        public HomeRecycleHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_recyclerView_item_imageview);
            textView = itemView.findViewById(R.id.home_recyclerView_item_textview);
            textView.setSelected(true);
            itemLayout = itemView.findViewById(R.id.home_recyclerView_item_layout);

        }
    }

    public interface  OnItemClickListener{
        void onClick(int position);
//        void onLongClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
