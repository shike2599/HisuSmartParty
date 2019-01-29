package com.hisu.smart.dj.ui.study.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.TopicPlanEntity;
import com.hisu.smart.dj.ui.adapter.StudyTopicAdapter;
import com.hisu.smart.dj.ui.study.contract.StudyTopicContract;
import com.hisu.smart.dj.ui.study.model.StudyTopicModel;
import com.hisu.smart.dj.ui.study.presenter.StudyTopicPresenter;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author lichee
 */
public class StudyTopicActivity extends BaseActivity<StudyTopicPresenter,StudyTopicModel> implements StudyTopicContract.View {

    @Bind(R.id.thematic_banner)
    Banner thematic_banner;
    @Bind(R.id.thematic_recycle_view)
    RecyclerView recyclerView;


    private List<Integer> thematicBannerImages;
    private int[] images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};

    StudyTopicAdapter topicAdapter;
    private List<TopicPlanEntity> topicPlanEntityList;


    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, StudyTopicActivity.class);
        activity.startActivity(intent);
    }

    private void initData() {
        topicPlanEntityList = new ArrayList<>();
        thematicBannerImages = new ArrayList<>();
        for(int i = 0; i < images.length; i++){
            thematicBannerImages.add(images[i]);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_topic;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
        initData();
    }

    @Override
    public void initView() {
        BannerWidget.setBanner(thematic_banner,thematicBannerImages);
        topicAdapter = new StudyTopicAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(topicAdapter);
        mPresenter.getMemberTopicDataRequest(AppConfig.getInstance().getInt(AppConstant.USER_ID,0),1,10);
    }

    @Override
    public void showLoading(String tag) {

    }

    @Override
    public void stopLoading(String tag) {

    }

    @Override
    public void showErrorTip(String msg, String tag) {

    }

    @Override
    public void returnMemberTopicData(List<TopicPlanEntity> topicPlanEntitys) {
          if(topicPlanEntityList.size() > 0){
              topicPlanEntityList.clear();
          }
          topicPlanEntityList.addAll(topicPlanEntitys);
          topicAdapter.setData(topicPlanEntitys);
    }

    @Override
    public void returnBranchTopicData(List<TopicPlanEntity> topicPlanEntitys) {
        if(topicPlanEntityList.size() > 0){
            topicPlanEntityList.clear();
        }
        topicPlanEntityList.addAll(topicPlanEntitys);
        topicAdapter.setData(topicPlanEntitys);
    }
}
