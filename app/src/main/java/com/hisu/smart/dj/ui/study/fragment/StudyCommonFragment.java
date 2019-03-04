package com.hisu.smart.dj.ui.study.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.MediaParamEntity;
import com.hisu.smart.dj.entity.StudyPlanEntity;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.adapter.StudyTopicAdapter;
import com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity;
import com.hisu.smart.dj.ui.study.contract.StudyCommonContract;
import com.hisu.smart.dj.ui.study.model.StudyCommonModel;
import com.hisu.smart.dj.ui.study.presenter.StudyCommonPresenter;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseFragment;
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
public class StudyCommonFragment extends BaseFragment<StudyCommonPresenter, StudyCommonModel> implements StudyCommonContract.View, OnLoadMoreListener, OnRefreshListener, StudyTopicAdapter.OnTopicItemClickListener {


    @Bind(R.id.common_recycle_view)
    IRecyclerView common_recycle_view;
    @Bind(R.id.common_loadedTip)
    LoadingTip loadingTip;
    private Banner commonBanner;
    private int totalPages;
    private static String TAG = "StudyCommonFragment";
    private List<Integer> bannerImages;
    private int[] images = {
            R.mipmap.home_banner_1, R.mipmap.home_banner_1,
            R.mipmap.home_banner_1, R.mipmap.home_banner_1};
    private String cateCode;
    StudyTopicAdapter commonAdapter;
    private static int SIZE = 6;
    private int mStartPage = 1;
    private int resId;
    private List<StudyPlanEntity> informations;

    public StudyCommonFragment() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_study_common;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
        initData();
    }

    @Override
    protected void initView() {
        Banner commonBanner = (Banner) LayoutInflater.from(getActivity()).inflate(R.layout.layout_banner_view, common_recycle_view.getHeaderContainer(), false);
        if (getArguments() != null) {
            cateCode = getArguments().getString(AppConstant.COMMON_CATE_CODE);
        }
        commonAdapter = new StudyTopicAdapter(getActivity());
        commonAdapter.setOnItemClickListener(this);
        common_recycle_view.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        common_recycle_view.removeHeaderAllView();
        common_recycle_view.addHeaderView(commonBanner);
        BannerWidget.setBanner(commonBanner, bannerImages);
        common_recycle_view.setAdapter(commonAdapter);
        common_recycle_view.setOnLoadMoreListener(this);
        common_recycle_view.setOnRefreshListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        //数据为空才重新发起请求
        if (commonAdapter.getSize() == 0) {
            mStartPage = 0;
            mPresenter.listCommonContentRequest(null, cateCode, "", mStartPage, SIZE);
        }else{
            mPresenter.getResVisitNumRequest(1, resId);
        }
    }

    private void initData() {
        bannerImages = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            bannerImages.add(images[i]);
        }
    }

    @Override
    public void showLoading(String tag) {
        if (commonAdapter.getPageBean().isRefresh()) {
            if (commonAdapter.getSize() <= 0) {
                loadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading(String msg) {
        if ("".equals(msg)) {
            loadingTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        } else {
            loadingTip.setLoadingTip(LoadingTip.LoadStatus.empty);
        }
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        if (commonAdapter.getPageBean().isRefresh()) {
            if (commonAdapter.getSize() <= 0) {
                loadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadingTip.setTips(msg);
            }
            common_recycle_view.setRefreshing(false);
        } else {
            common_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void returnlistCommonContent(InformationResponse<StudyPlanEntity> informationResponse) {
        informations = informationResponse.getDataList();
        totalPages = informationResponse.getTotalPage();
        if (informations != null) {
            LogUtils.logd("returnlistCommonContent======" + informations.size());
            mStartPage += 1;
            if (commonAdapter.getPageBean().isRefresh()) {
                common_recycle_view.setRefreshing(false);
                int size = informations.size();
                String ids = "";
                for (int i = 0; i < size; i++) {
                    ids += informations.get(i).getId() + ",";
                }
                if (ids.endsWith(",")) {
                    ids = ids.substring(0, ids.lastIndexOf(","));
                }
                mPresenter.getAllResVisitNumRequest("1", 1, ids);
                //commonAdapter.setData(informations);
            } else {
                if (informations.size() > 0) {
                    common_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    int size = informations.size();
                    String ids = "";
                    for (int i = 0; i < size; i++) {
                        ids += informations.get(i).getId() + ",";
                    }
                    if (ids.endsWith(",")) {
                        ids = ids.substring(0, ids.lastIndexOf(","));
                    }
                    mPresenter.getAllResVisitNumRequest("2", 1, ids);
                    //commonAdapter.addAll(informations);
                } else {
                    common_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        } else {
            loadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
        }

    }



    @Override
    public void returnResVisitNum(VisitNumResponse visitNumResponse) {
        int num = visitNumResponse.getData();
        List<StudyPlanEntity> informationList = commonAdapter.getData();
        if (informationList != null) {
            for (StudyPlanEntity entity : informationList) {
                if (entity.getId() == resId) {
                    entity.setWatchNum(num);
                }
            }
        }
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnAddResVisitNum(BaseResponse response) {
        Log.i(TAG,"returnAddResVisitNum================="+response.getResultCode());
    }

    @Override
    public void returnAllResVisitNum(BaseResponse<VisitNumEntity> baseResponse, String code) {
        List<VisitNumEntity> visitNumEntities = baseResponse.getDataList();
        if (visitNumEntities != null && visitNumEntities.size() > 0) {
            int size = visitNumEntities.size();
            if (code != null && code.equals("1")) {
                if (informations == null) {
                    return;
                }
                int size2 = informations.size();
                for (int i = 0; i < size; i++) {
                    VisitNumEntity visitNumEntity = visitNumEntities.get(i);
                    for (int j = 0; j < size2; j++) {
                        StudyPlanEntity informationEntity = informations.get(j);
                        if (informationEntity.getId() == visitNumEntity.getId()) {
                            Log.i(TAG, "returnAllResVisitNum==========resId:" + visitNumEntity.getId() + ",watchNum:" + visitNumEntity.getNum());
                            informationEntity.setWatchNum(visitNumEntity.getNum());
                        }
                    }
                }
                commonAdapter.setData(informations);
            } else if (code != null && code.equals("2")) {
                if (informations == null) {
                    return;
                }
                int size2 = informations.size();
                for (int i = 0; i < size; i++) {
                    VisitNumEntity visitNumEntity = visitNumEntities.get(i);
                    for (int j = 0; j < size2; j++) {
                        StudyPlanEntity informationEntity = informations.get(j);
                        if (informationEntity.getId() == visitNumEntity.getId()) {
                            Log.i(TAG, "returnAllResVisitNum==========resId:" + visitNumEntity.getId() + ",watchNum:" + visitNumEntity.getNum());
                            informationEntity.setWatchNum(visitNumEntity.getNum());
                        }
                    }
                }
                commonAdapter.addAll(informations);
            }
        } else {
            if (code != null && code.equals("1")) {
                commonAdapter.setData(informations);
            } else if (code != null && code.equals("2")) {
                commonAdapter.addAll(informations);
            }
        }
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        commonAdapter.getPageBean().setRefresh(false);
        //发起请求
        Log.d("page","totalpage="+totalPages+",startPage="+mStartPage);
        if (totalPages > mStartPage) {
            common_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
            mPresenter.listCommonContentRequest(null, cateCode, "", mStartPage, SIZE);
        } else {
            common_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    @Override
    public void onRefresh() {
        commonAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        common_recycle_view.setRefreshing(true);
        mPresenter.listCommonContentRequest(null, cateCode, "", mStartPage, SIZE);
        common_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
    }

    @Override
    public void onTopicClick(int position, StudyPlanEntity data) {
        resId = data.getId();
        mPresenter.getAddResVisitNumRequest(1,resId);
        if(data.getMediaType() == 0){
            MediaParamEntity info = new MediaParamEntity();
            info.setUrl(data.getUrl());
            info.setTitle(data.getName());
            info.setResId(data.getId());
            info.setResType(1);
            info.setCover(data.getIcon());
            info.setUserId(AppConfig.getInstance().getInt(AppConstant.USER_ID,-1));
            info.setCreateTime(data.getPublishTime());
            info.setTotalHours(data.getTotalHours());
            MediaPlayerActivity.startAction(getActivity(), info);
        }else{
            WebActivity.startAction(getActivity(),data.getId(),"常规学习");
        }
    }
}
