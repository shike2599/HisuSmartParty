package com.hisu.smart.dj.ui.study.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.MediaParamEntity;
import com.hisu.smart.dj.entity.StudyPlanEntity;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.adapter.StudyTopicAdapter;
import com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity;
import com.hisu.smart.dj.ui.study.contract.StudyTopicContract;
import com.hisu.smart.dj.ui.study.model.StudyTopicModel;
import com.hisu.smart.dj.ui.study.presenter.StudyTopicPresenter;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author lichee
 */
public class StudyTopicActivity extends BaseActivity<StudyTopicPresenter,StudyTopicModel> implements
        StudyTopicContract.View, OnRefreshListener, OnLoadMoreListener,
        StudyTopicAdapter.OnTopicItemClickListener {


    @Bind(R.id.thematic_recycle_view)
    IRecyclerView recyclerView;
    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView backImage;
    @Bind(R.id.topic_loadedTip)
    LoadingTip loadingTip;
    private int totalPages ;
    private Banner thematic_banner;
    private List<Integer> thematicBannerImages;
    private int[] images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};

    StudyTopicAdapter topicAdapter;
    private static int SIZE = 6;
    private int mStartPage = 1;
    private int resId;
    private List<StudyPlanEntity> topicPlanEntitys;
    private final static String TAG = "StudyTopicActivity";

    public static void startAction(Activity activity,boolean isPartyBranch){
        Intent intent = new Intent(activity, StudyTopicActivity.class);
        intent.putExtra(AppConstant.IS_PARTY_BRANCH,isPartyBranch);
        activity.startActivity(intent);
    }

    private void initData() {
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
        mTitle.setText("专题学习");
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        thematic_banner =  (Banner) LayoutInflater.from(this).inflate(R.layout.layout_banner_view, recyclerView.getHeaderContainer(), false);
        BannerWidget.setBanner(thematic_banner,thematicBannerImages);
        recyclerView.removeHeaderAllView();
        recyclerView.addHeaderView(thematic_banner);
        topicAdapter = new StudyTopicAdapter(this);
        topicAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(topicAdapter);
        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setOnRefreshListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(topicAdapter.getSize() == 0){
            if(AppConstant.IS_STUDY_BRANCH){
                mPresenter.getBranchTopicDataRequest(AppConfig.getInstance().getInt(AppConstant.USER_ID,0),mStartPage,SIZE);
            }else{
                mPresenter.getMemberTopicDataRequest(AppConfig.getInstance().getInt(AppConstant.USER_ID,0),mStartPage,SIZE);
            }
        }else{
            mPresenter.getResVisitNumRequest(2,resId);
        }
    }

    @Override
    public void showLoading(String tag) {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading(String msg) {
        if("".equals(msg)){
            loadingTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }else{
            loadingTip.setLoadingTip(LoadingTip.LoadStatus.empty);
        }
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        if( topicAdapter.getPageBean().isRefresh()) {
            if(topicAdapter.getSize()<=0) {
                loadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadingTip.setTips(msg);
            }
            recyclerView.setRefreshing(false);
        }else{
            recyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void returnMemberTopicData(InformationResponse<StudyPlanEntity> informationResponse) {
        topicPlanEntitys = informationResponse.getDataList();
        totalPages = informationResponse.getTotalPage();
        if (topicPlanEntitys != null) {
            Log.i(TAG,"returnMemberTopicData======"+topicPlanEntitys.size());
            mStartPage += 1;
            if (topicAdapter.getPageBean().isRefresh()) {
                recyclerView.setRefreshing(false);
                int size = topicPlanEntitys.size();
                String ids = "";
                for (int i = 0; i < size; i++) {
                    ids += topicPlanEntitys.get(i).getId() + ",";
                }
                if (ids.endsWith(",")) {
                    ids = ids.substring(0, ids.lastIndexOf(","));
                }
                Log.i(TAG,"ids1==================="+ids);
                mPresenter.getAllResVisitNumRequest("1", 2, ids);
            } else {
                if (topicPlanEntitys.size() > 0) {
                    recyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    int size = topicPlanEntitys.size();
                    String ids = "";
                    for (int i = 0; i < size; i++) {
                        ids += topicPlanEntitys.get(i).getId() + ",";
                    }
                    if (ids.endsWith(",")) {
                        ids = ids.substring(0, ids.lastIndexOf(","));
                    }
                    Log.i(TAG,"ids2==================="+ids);
                    mPresenter.getAllResVisitNumRequest("2", 2, ids);
                } else {
                    recyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }

    }

    @Override
    public void returnBranchTopicData(InformationResponse<StudyPlanEntity> informationResponse) {
        topicPlanEntitys = informationResponse.getDataList();
        totalPages = informationResponse.getTotalPage();
        if (topicPlanEntitys != null) {
            Log.i(TAG,"returnBranchTopicData======"+topicPlanEntitys.size());
            mStartPage +=1;
            if (topicAdapter.getPageBean().isRefresh()) {
                recyclerView.setRefreshing(false);
                int size = topicPlanEntitys.size();
                String ids = "";
                for (int i = 0; i < size; i++) {
                    ids += topicPlanEntitys.get(i).getId() + ",";
                }
                if (ids.endsWith(",")) {
                    ids = ids.substring(0, ids.lastIndexOf(","));
                }
                Log.i(TAG,"ids3============="+ids);
                mPresenter.getAllResVisitNumRequest("1", 2, ids);
            } else {
                if (topicPlanEntitys.size() > 0) {
                    recyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    int size = topicPlanEntitys.size();
                    String ids = "";
                    for (int i = 0; i < size; i++) {
                        ids += topicPlanEntitys.get(i).getId() + ",";
                    }
                    if (ids.endsWith(",")) {
                        ids = ids.substring(0, ids.lastIndexOf(","));
                    }
                    Log.i(TAG,"ids4============="+ids);
                    mPresenter.getAllResVisitNumRequest("2", 2, ids);
                } else {
                    recyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }


    @Override
    public void returnResVisitNum(VisitNumResponse visitNumResponse) {
        int num = visitNumResponse.getData();
        List<StudyPlanEntity> informationList = topicAdapter.getData();
        if (informationList != null) {
            for (StudyPlanEntity entity : informationList) {
                if (entity.getId() == resId) {
                    entity.setWatchNum(num);
                }
            }
        }
        topicAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnAddResVisitNum(BaseResponse response) {
        Log.i(TAG,"returnAddResVisitNum================="+response.getResultCode());
    }

    @Override
    public void returnAllResVisitNum(BaseResponse<VisitNumEntity> baseResponse, String code) {
        List<VisitNumEntity> visitNumEntities = baseResponse.getDataList();
        if (visitNumEntities != null) {
            Log.i(TAG,"returnAllResVisitNum======================="+visitNumEntities.toString());
            int size = visitNumEntities.size();
            if (code != null && code.equals("1")) {
                if (topicPlanEntitys == null) {
                    return;
                }
                int size2 = topicPlanEntitys.size();
                for (int i = 0; i < size; i++) {
                    VisitNumEntity visitNumEntity = visitNumEntities.get(i);
                    for (int j = 0; j < size2; j++) {
                        StudyPlanEntity informationEntity = topicPlanEntitys.get(j);
                        if (informationEntity.getId() == visitNumEntity.getId()) {
                            Log.i(TAG, "returnAllResVisitNum==========resId:" + visitNumEntity.getId() + ",watchNum:" + visitNumEntity.getNum());
                            informationEntity.setWatchNum(visitNumEntity.getNum());
                        }
                    }
                }
                topicAdapter.setData(topicPlanEntitys);
            } else if (code != null && code.equals("2")) {
                if (topicPlanEntitys == null) {
                    return;
                }
                int size2 = topicPlanEntitys.size();
                for (int i = 0; i < size; i++) {
                    VisitNumEntity visitNumEntity = visitNumEntities.get(i);
                    for (int j = 0; j < size2; j++) {
                        StudyPlanEntity informationEntity = topicPlanEntitys.get(j);
                        if (informationEntity.getId() == visitNumEntity.getId()) {
                            Log.i(TAG, "returnAllResVisitNum==========resId:" + visitNumEntity.getId() + ",watchNum:" + visitNumEntity.getNum());
                            informationEntity.setWatchNum(visitNumEntity.getNum());
                        }
                    }
                }
                topicAdapter.addAll(topicPlanEntitys);
            }
        } else {
            if (code != null && code.equals("1")) {
                topicAdapter.setData(topicPlanEntitys);
            } else if (code != null && code.equals("2")) {
                topicAdapter.addAll(topicPlanEntitys);
            }
        }
    }

    @Override
    public void onRefresh() {
        topicAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        recyclerView.setRefreshing(true);
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        if(AppConstant.IS_STUDY_BRANCH){
            mPresenter.getBranchTopicDataRequest(AppConfig.getInstance().getInt(AppConstant.USER_ID,0),mStartPage,SIZE);
        }else{
            mPresenter.getMemberTopicDataRequest(AppConfig.getInstance().getInt(AppConstant.USER_ID,0),mStartPage,SIZE);
        }
        recyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        topicAdapter.getPageBean().setRefresh(false);
        //发起请求
        if(totalPages >= mStartPage){
            if(getIntent().getBooleanExtra(AppConstant.IS_PARTY_BRANCH,false)){
                mPresenter.getBranchTopicDataRequest(AppConfig.getInstance().getInt(AppConstant.USER_ID,0),mStartPage,SIZE);
            }else{
                mPresenter.getMemberTopicDataRequest(AppConfig.getInstance().getInt(AppConstant.USER_ID,0),mStartPage,SIZE);
            }
            recyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        }else{
            recyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }


    @Override
    public void onTopicClick(int position, StudyPlanEntity data) {
        resId = data.getId();
        Log.i(TAG,"onTopicClick=========================="+resId);
        mPresenter.getAddResVisitNumRequest(2,resId);
        if(data.getMediaType() == 0){
            MediaParamEntity info = new MediaParamEntity();
            info.setUrl(data.getUrl());
            info.setTitle(data.getName());
            info.setResId(data.getId());
            info.setResType(2);
            info.setCover(data.getIcon());
            info.setUserId(AppConfig.getInstance().getInt(AppConstant.USER_ID,-1));
            info.setCreateTime(data.getPublishTime());
            info.setTotalHours(data.getTotalHours());
            MediaPlayerActivity.startAction(this, info);
        }else {
            WebActivity.startAction(this, data.getId(), "专题学习");
        }
    }
}
