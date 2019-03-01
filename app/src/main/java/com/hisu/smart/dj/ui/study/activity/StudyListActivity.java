package com.hisu.smart.dj.ui.study.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.MediaParamEntity;
import com.hisu.smart.dj.entity.StudyListEntity;
import com.hisu.smart.dj.ui.adapter.StudyListRecyclerAdapter;
import com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity;
import com.hisu.smart.dj.ui.study.contract.StudyListContract;
import com.hisu.smart.dj.ui.study.model.StudyListModel;
import com.hisu.smart.dj.ui.study.presenter.StudyListPresenter;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.NetWorkUtils;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class StudyListActivity extends BaseActivity<StudyListPresenter,StudyListModel>
        implements View.OnClickListener,StudyListContract.View,
        OnBannerListener,OnRefreshListener,OnLoadMoreListener,
        StudyListRecyclerAdapter.OnStudyItemClickListener{


    @Bind(R.id.title_TextView)
    TextView title;
    @Bind(R.id.back_imageView)
    ImageView back_img;
    @Bind(R.id.study_recycle_view)
    IRecyclerView studyIRecyclerView;
    @Bind(R.id.study_loadedTip)
    LoadingTip study_LoadTip;
    @Bind(R.id.study_upLoad_imageView)
    ImageView upload_img;
    private List<Integer> StudyBannerImages;
    private Banner studyBanner;
    private int[] images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};
    private static int SIZE = 20;
    private int dateSize = 0;
    private int lastDateId;
    private String lastDateTime;
    private StudyListRecyclerAdapter studyRecyclerAdapter;
    private int partyMemberId;
    private static final String CATECODE = "5008";
    private boolean isFirst = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_study_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
        partyMemberId = AppConfig.getInstance().getInt(AppConstant.MEMBER_ID,-1);
        studyRecyclerAdapter = new StudyListRecyclerAdapter(this);
        studyRecyclerAdapter.setOnItemClickListener(this);
        initData();
    }

    private void initData() {
        StudyBannerImages = new ArrayList<>();
        for(int i = 0; i < images.length; i++){
            StudyBannerImages.add(images[i]);
        }
    }

    @Override
    public void initView() {
        title.setText("学习心得");
        back_img.setOnClickListener(this);
        upload_img.setOnClickListener(this);

        studyBanner = (Banner) LayoutInflater.from(this).inflate(R.layout.layout_banner_view, studyIRecyclerView.getHeaderContainer(), false);
        studyIRecyclerView.addHeaderView(studyBanner);
        studyBanner.setOnBannerListener(this);
        BannerWidget.setBanner(studyBanner,StudyBannerImages);

        studyIRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studyIRecyclerView.setAdapter(studyRecyclerAdapter);
        studyIRecyclerView.setOnLoadMoreListener(this);
        studyIRecyclerView.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("StudyListActivity","-----onResume()----");
        if(NetWorkUtils.isNetConnected(AppApplication.getAppContext())){
            isFirst = true;
            studyRecyclerAdapter.getPageBean().setRefresh(true);//每次请求时先刷新数据
            mPresenter.getListMemberActionWithPulledRequest(partyMemberId,null,CATECODE,null,null,SIZE);
        }else{
            Toast.makeText(this,"网络异常!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imageView:
                StudyListActivity.this.finish();
                break;
            case R.id.study_upLoad_imageView:
                StudyExperienceActivity.startAction(this,"学习心得",Integer.valueOf("5008"));
                break;
        }
    }
    //轮播图点击事件
    @Override
    public void OnBannerClick(int position) {

    }
    //加载
    @Override
    public void onLoadMore(View loadMoreView) {
        studyRecyclerAdapter.getPageBean().setRefresh(false);
        isFirst = false;
        //发起请求
        if(dateSize == 20){
            mPresenter.getListMemberActionWithPulledRequest(partyMemberId,null,CATECODE,lastDateId,lastDateTime,SIZE);
            studyIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        }else{
            studyIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }
    //刷新
    @Override
    public void onRefresh() {
        isFirst = false;
        studyRecyclerAdapter.getPageBean().setRefresh(true);
        //发起请求
        studyIRecyclerView.setRefreshing(true);
        mPresenter.getListMemberActionWithPulledRequest(partyMemberId,null,CATECODE,null,null,SIZE);
        studyIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
    }

    @Override
    public void returnMemberActionWithPulled(StudyListEntity studyListEntity) {
        List<StudyListEntity.DataListBean> studyBeans = studyListEntity.getDataList();
        Log.d("StudyListActivity","informations--size()===="+studyBeans.size());
        if(studyBeans != null && studyBeans.size()>0){
            dateSize = studyBeans.size();
            lastDateId = studyBeans.get(dateSize-1).getId();
            lastDateTime = studyBeans.get(dateSize-1).getPublishTime();
            if (studyRecyclerAdapter.getPageBean().isRefresh()) {
                Log.d("StudyListActivity","informations--size()===="+studyBeans.size());
                studyIRecyclerView.setRefreshing(false);
                studyRecyclerAdapter.setData(studyBeans);
            } else {
                if (studyBeans.size() > 0) {
                    studyIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    studyRecyclerAdapter.addAll(studyBeans);
                } else {
                    Log.d("StudyListActivity","informations--size()====000");
                    studyIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }else{
            studyIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    @Override
    public void showLoading(String tag) {
        if(isFirst){
            study_LoadTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }

    }

    @Override
    public void stopLoading(String tag) {
        if(isFirst){
            study_LoadTip.setLoadingTip(LoadingTip.LoadStatus.finish);

        }
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        if(isFirst){
            study_LoadTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public static void startAction(Activity activity, String title){
        Intent intent = new Intent(activity, StudyListActivity.class);
        intent.putExtra(AppConstant.SHOW_TITLE,title);
        activity.startActivity(intent);
    }
    //item点击事件
    @Override
    public void onStudyItemClick(int position, StudyListEntity.DataListBean studyBean) {
            if(studyBean.getMediaType() == 0){
                MediaParamEntity info = new MediaParamEntity();
                info.setUrl(studyBean.getUrl());
                info.setTitle(studyBean.getName());
                info.setResId(studyBean.getId());
                info.setResType(0);
                if(studyBean.getImages()!=null&&studyBean.getImages().size()>0){
                    info.setCover((String) studyBean.getImages().get(0));
                }
                info.setUserId(AppConfig.getInstance().getInt(AppConstant.USER_ID,-1));
                info.setCreateTime(studyBean.getPublishTime());
                MediaPlayerActivity.startAction(this, info);
            }else {
                WebActivity.startAction(this, studyBean.getId(),"践行活动");
            }
    }
}
