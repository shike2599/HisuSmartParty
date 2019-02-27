package com.hisu.smart.dj.ui.main.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.GridViewItemEntity;
import com.hisu.smart.dj.ui.adapter.GridViewAdapter;
import com.hisu.smart.dj.ui.news.activity.NewsActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseFragment;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


/**
 * 践行
 * @author lichee
 */
public class FollowFragment extends BaseFragment {


    private static final String TAG = "FollowFragment";

    private Banner followBanner;
    private List<Integer> followBannerImages;
    private int[] Images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};

    private GridView gridView;

    private List<GridViewItemEntity> gridViewItemEntities;
    private GridViewAdapter gridViewAdapter ;
    private int followId;//分类ID 本职工作=1，党组工作=2，志愿者活动=5，脱贫攻坚=4 困难帮扶=6 典型事迹=3
    private int cateId;
    private String title_str;

    public FollowFragment() {
    }
    private void initBannerData() {
        followBannerImages = new ArrayList<>();
        for(int i = 0; i < Images.length; i++){
            followBannerImages.add(Images[i]);
        }
    }
    private void initGridViewData() {
        gridViewItemEntities = new ArrayList<>();
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.self_works,"本职工作","规划学习,有的放矢"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.dang_works,"党组工作","参与活动,交流学习经验"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.volunteer_events,"志愿者活动","提醒&签到,参会准时高效"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.efforts_poor,"脱贫攻坚","坚持学习,每日一课"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.help_hard,"困难帮扶","学习时政,关注党政大事"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.typical_events,"典型事迹","在线测试你的学习成果"));
    }

    private void initData() {
        initBannerData();
        initGridViewData();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_follow;
    }

    @Override
    public void initPresenter() {
        initData();
    }

    @Override
    protected void initView() {
        followBanner = rootView.findViewById(R.id.follow_banner);
        BannerWidget.setBanner(followBanner,followBannerImages);
        gridView = rootView.findViewById(R.id.follow_gridview);
        gridViewAdapter = new GridViewAdapter(getActivity());
        gridViewAdapter.setGridViewItemEntities(gridViewItemEntities);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.item_title);
                String follow_str = textView.getText().toString();
                if(follow_str.equals("本职工作")){
                    followId = 5001;
//                    cateId = 1;
                    title_str = "本职工作";
                }else if(follow_str.equals("党组工作")){
                    followId = 5002;
//                    cateId = 2;
                    title_str = "党组工作";
                }else if(follow_str.equals("志愿者活动")){
                    followId = 5005;
//                    cateId = 5;
                    title_str = "志愿者活动";
                }else if(follow_str.equals("脱贫攻坚")){
                    followId = 5004;
//                    cateId = 4;
                    title_str = "脱贫攻坚";
                }else if(follow_str.equals("困难帮扶")){
                    followId = 5006;
//                    cateId = 6;
                    title_str = "困难帮扶";
                }else if(follow_str.equals("典型事迹")){
                    followId = 5003;
//                    cateId = 3;
                    title_str = "典型事迹";
                }
                NewsActivity.startAction(getActivity(),title_str,followId);
            }
        });
    }

}
