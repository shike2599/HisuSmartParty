package com.hisu.smart.dj.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.GridViewItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lichee
 * @date 2019/1/22
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<GridViewItemEntity> gridViewItemEntities;

    public GridViewAdapter(Context mContext) {
        this.mContext = mContext;
        gridViewItemEntities = new ArrayList<>();
    }

    public void setGridViewItemEntities(List<GridViewItemEntity> gridViewItemEntities) {
        this.gridViewItemEntities = gridViewItemEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gridViewItemEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return gridViewItemEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.grid_view_item, null);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_title);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.item_desc);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.icon.setImageResource(gridViewItemEntities.get(position).getIcon());
        viewHolder.title.setText(gridViewItemEntities.get(position).getTitle());
        viewHolder.desc.setText(gridViewItemEntities.get(position).getDesc());
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView title;
        TextView desc;
    }
}
