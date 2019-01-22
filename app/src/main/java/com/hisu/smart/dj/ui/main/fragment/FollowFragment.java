package com.hisu.smart.dj.ui.main.fragment;

import android.widget.GridView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.GridViewItemEntity;
import com.hisu.smart.dj.ui.main.adapter.GridViewAdapter;
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
            R.mipmap.home_selected_ico,R.mipmap.study_selected_icon,
            R.mipmap.dang_selected_icon,R.mipmap.doit_selected_icon};

    private GridView gridView;

    private List<GridViewItemEntity> gridViewItemEntities;
    private GridViewAdapter gridViewAdapter ;

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
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"本职工作","规划学习,有的放矢"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"党组工作","参与活动,交流学习经验"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"志愿者活动","提醒&签到,参会准时高效"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"脱贫攻坚","坚持学习,每日一课"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"困难帮扶","学习时政,关注党政大事"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"典型事迹","在线测试你的学习成果"));
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
    }

}
