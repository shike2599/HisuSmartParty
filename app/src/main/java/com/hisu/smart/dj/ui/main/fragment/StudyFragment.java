package com.hisu.smart.dj.ui.main.fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.GridViewItemEntity;
import com.hisu.smart.dj.ui.adapter.GridViewAdapter;
import com.hisu.smart.dj.ui.iactive.activity.IactiveLoginActivity;
import com.hisu.smart.dj.ui.news.activity.NewsActivity;
import com.hisu.smart.dj.ui.study.activity.StudyCommonActivity;
import com.hisu.smart.dj.ui.study.activity.StudyExperienceActivity;
import com.hisu.smart.dj.ui.study.activity.StudyPlanActivity;
import com.hisu.smart.dj.ui.study.activity.LearningRankingActivity;
import com.hisu.smart.dj.ui.study.activity.StudyTopicActivity;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.hisu.smart.dj.ui.widget.MyGridView;
import com.jaydenxiao.common.base.BaseFragment;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


/**
 * 学习
 * @author lichee
 */
public class StudyFragment extends BaseFragment implements View.OnClickListener{
    private static final String mHomeUrl = "http://app.html5.qq.com/navi/index";
    private static final String BaseStudy = "subjectStudy/";
//    //专题学习页面
//    private static final String normalStudy = AppConstant.BASE_URL+BaseStudy+"normalStudy.html";
//    //常规学习页面
//    private static final String studyList = AppConstant.BASE_URL_LOAD+BaseStudy+"studyList.html";
//    //三会一课页面
//    private static final String study_scale = AppConstant.BASE_URL+BaseStudy+"normalStudy.html";
    //在线考试页面
    private static final String online_exam = AppConstant.BASE_URL_LOAD+"study/studyExamination.html";


    private static final String TAG = "StudyFragment";
    private Context context;
    private Banner studyBanner;
    private RelativeLayout change_Layout;
    private TextView change_state_textView;
    private List<Integer> studyBannerImages;
    private int[] Images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};

    private MyGridView gridView;
    private List<GridViewItemEntity> gridViewItemEntities;
    private GridViewAdapter gridViewAdapter ;
    private boolean isPartyBranch;
    private boolean isPartyCommittee;
    private NestedScrollView nestedScrollView;
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
        context = getActivity();
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
        if(!AppConstant.IS_STUDY_BRANCH){
            gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.online_exam,"在线考试","在线测试你的学习成果"));
        }
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.study_ranking,"学习排名","好好学习,天天向上"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.study_sum,"学习心得","总结提长,记录成长"));
    }


    @Override
    public void initPresenter() {
        initData();
    }

    @Override
    protected void initView() {
        change_Layout = rootView.findViewById(R.id.change_study_state_Layout);
        nestedScrollView = rootView.findViewById(R.id.study_ScrollView);
        //判断是否是党员账号
        isPartyBranch =  AppConfig.getInstance().getBoolean(AppConstant.IS_PARTY_BRANCH,false);
        isPartyCommittee =  AppConfig.getInstance().getBoolean(AppConstant.IS_PARTY_COMMITTEE,false);
        if(isPartyBranch || isPartyCommittee){
            change_Layout.setVisibility(View.VISIBLE);
        }else{
            change_Layout.setVisibility(View.GONE);
        }
        change_Layout.setOnClickListener(this);
        change_state_textView = rootView.findViewById(R.id.study_member_or_branch_TextView);
        studyBanner = rootView.findViewById(R.id.study_banner);
        BannerWidget.setBanner(studyBanner,studyBannerImages);
        gridView = rootView.findViewById(R.id.study_gridview);
        nestedScrollView.requestFocus();
        gridView.setFocusable(false);
        gridViewAdapter = new GridViewAdapter(getActivity());
        gridViewAdapter.setGridViewItemEntities(gridViewItemEntities);
        gridView.setAdapter(gridViewAdapter);
        //党员学习点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context,"item-"+position,Toast.LENGTH_SHORT).show();
                TextView title_TextView =  view.findViewById(R.id.item_title);
                String title = title_TextView.getText().toString();
                if(title=="学习计划"){
                    //学习计划
                    StudyPlanActivity.startAction(getActivity());
                }else if(title=="专题学习"){
                    StudyTopicActivity.startAction(getActivity(), AppConfig.getInstance().getBoolean(AppConstant.IS_PARTY_BRANCH,false));
                }else if(title=="学习排名"){
                    //排名
                    LearningRankingActivity.startAction(getActivity());
                }else if(title=="学习心得"){
                    //心得
                    StudyExperienceActivity.startAction(getActivity(),"学习心得",5008);
                }else if(title=="三会一课"){
                    //三会一课
//                    WebActivity.startAction(getActivity(),"三会一课",mHomeUrl);
                    NewsActivity.startAction(getActivity(),"三会一课");
                }else if(title=="常规学习"){
                    //常规学习
                    StudyCommonActivity.startAction(getActivity());
                }else if(title=="视频大讲堂"){
                    //视频会议
                    IactiveLoginActivity.startAction(getActivity(),1);
                }else if(title=="在线考试"){
                    //在线考试
                    WebActivity.startAction(getActivity(),"在线考试",online_exam );
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_study_state_Layout:
                if(AppConstant.IS_STUDY_BRANCH){
                    change_state_textView.setText("支部学习");
                    AppConstant.IS_STUDY_BRANCH = false;
                }else{
                    change_state_textView.setText("党员学习");
                    AppConstant.IS_STUDY_BRANCH = true;
                }
                initGridViewData();
                gridViewAdapter.setGridViewItemEntities(gridViewItemEntities);
                break;
        }
    }
}
