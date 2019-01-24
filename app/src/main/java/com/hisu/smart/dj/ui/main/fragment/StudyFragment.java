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
 * 学习
 * @author lichee
 */
public class StudyFragment extends BaseFragment {

    private static final String TAG = "StudyFragment";
    private Banner studyBanner;
    private List<Integer> studyBannerImages;
    private int[] Images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};

    private GridView gridView;

    private List<GridViewItemEntity> gridViewItemEntities;
    private GridViewAdapter gridViewAdapter ;
    public StudyFragment() {
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_study;
    }

    private void initBannerData() {
        studyBannerImages = new ArrayList<>();
        for(int i = 0; i < Images.length; i++){
            studyBannerImages.add(Images[i]);
        }
    }

    private void initData() {
        initBannerData();
        initGridViewData();
    }

    private void initGridViewData() {
        gridViewItemEntities = new ArrayList<>();
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.study_plan,"学习计划","规划学习,有的放矢"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.study_speial,"专题学习","参与活动,交流学习经验"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.study_scale,"三会一课","提醒&签到,参会准时高效"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.study_normal,"常规学习","坚持学习,每日一课"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.online_video,"视频大讲堂","学习时政,关注党政大事"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.online_exam,"在线考试","在线测试你的学习成果"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.study_ranking,"学习排名","好好学习,天天向上"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.study_sum,"学习心得","总结提长,记录成长"));
    }


    @Override
    public void initPresenter() {
        initData();
    }


    @Override
    protected void initView() {
        studyBanner = rootView.findViewById(R.id.study_banner);
        BannerWidget.setBanner(studyBanner,studyBannerImages);
        gridView = rootView.findViewById(R.id.study_gridview);
        gridViewAdapter = new GridViewAdapter(getActivity());
        gridViewAdapter.setGridViewItemEntities(gridViewItemEntities);
        gridView.setAdapter(gridViewAdapter);
    }


}
