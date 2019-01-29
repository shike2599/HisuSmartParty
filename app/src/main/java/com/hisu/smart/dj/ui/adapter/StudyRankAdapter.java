package com.hisu.smart.dj.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.RankEntity;

import java.util.ArrayList;
import java.util.List;

public class StudyRankAdapter extends RecyclerView.Adapter<StudyRankAdapter.RankHolder> {
    private List<RankEntity> dataList;
    private int rank_type;
    public StudyRankAdapter(int type){
        rank_type = type;
        dataList = new ArrayList<>();
    }
    public void setData(List<RankEntity> list){
        dataList.clear();
        dataList.addAll(list) ;
        Log.d("StudyRankAdapter","dataList---size()===="+dataList.size());
        notifyDataSetChanged();
    }
     @Override
    public RankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rank_recycler_item,parent,false);
        RankHolder holder = new RankHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankHolder holder, int position) {
        RankEntity rankEntity = dataList.get(position);
//         if(position == 0){
//             holder.show_rank_img.setBackgroundResource(R.mipmap.study_ranking_1_icon);
//         }else if(position == 1){
//             holder.show_rank_img.setBackgroundResource(R.mipmap.study_ranking_2_iocn);
//         }else if(position == 2){
//             holder.show_rank_img.setBackgroundResource(R.mipmap.study_ranking_3_icon);
//             Log.d("StudyRankAdapter","22222222=====");
//         }else if(position >=3){
//             Log.d("StudyRankAdapter","11111111====="+position);
//             //holder.show_rank_img.setVisibility(View.GONE);
//             holder.show_rank.setText(String.valueOf(position+1));
//         }

        if(position == 0){
            holder.show_rank_img.setBackgroundResource(R.mipmap.study_ranking_1_icon);
            holder.show_rank.setVisibility(View.GONE);
        }

        if(position == 1){
            holder.show_rank_img.setBackgroundResource(R.mipmap.study_ranking_2_iocn);
            holder.show_rank.setVisibility(View.GONE);
        }
        if(position == 2){
            holder.show_rank_img.setBackgroundResource(R.mipmap.study_ranking_3_icon);
            holder.show_rank.setVisibility(View.GONE);
        }
        if(position >= 3){
            holder.show_rank.setText(String.valueOf(position+1));
        }
         holder.show_rank_name.setText(rankEntity.getName());
         if(rank_type == 0){
             //总学时排名
             holder.show_rank_state.setText(rankEntity.getTotalHours()+"");
         }else if(rank_type == 1){
             //常规排名
             holder.show_rank_state.setText(rankEntity.getCommHours()+"");
         }else if(rank_type == 2){
             //专题学习排名
             holder.show_rank_state.setText(rankEntity.getTopicHours()+"");
         }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

     class RankHolder extends RecyclerView.ViewHolder{
        private ImageView show_rank_img;
        private TextView show_rank;
        private TextView show_rank_name;
        private TextView show_rank_state;
        public RankHolder(View itemView) {
            super(itemView);
          show_rank = itemView.findViewById(R.id.show_rank);
          show_rank_name = itemView.findViewById(R.id.show_rank_name);
          show_rank_state = itemView.findViewById(R.id.show_rank_state);
          show_rank_img = itemView.findViewById(R.id.rank_img_ImageView);
        }
    }
}
