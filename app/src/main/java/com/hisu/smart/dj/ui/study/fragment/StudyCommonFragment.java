package com.hisu.smart.dj.ui.study.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.hisu.smart.dj.ui.adapter.StudyTopicAdapter;
import com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity;
import com.hisu.smart.dj.ui.study.contract.StudyCommonContract;
import com.hisu.smart.dj.ui.study.model.StudyCommonModel;
import com.hisu.smart.dj.ui.study.presenter.StudyCommonPresenter;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseFragment;
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

    @Bind(R.id.common_banner)
    Banner commonBanner;
    @Bind(R.id.common_recycle_view)
    IRecyclerView common_recycle_view;
    @Bind(R.id.common_loadedTip)
    LoadingTip loadingTip;
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
        commonBanner = rootView.findViewById(R.id.common_banner);
        BannerWidget.setBanner(commonBanner, bannerImages);
        if (getArguments() != null) {
            cateCode = getArguments().getString(AppConstant.COMMON_CATE_CODE);
        }
        commonAdapter = new StudyTopicAdapter(getActivity());
        commonAdapter.setOnItemClickListener(this);
        common_recycle_view.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        common_recycle_view.setAdapter(commonAdapter);
        common_recycle_view.setOnLoadMoreListener(this);
        common_recycle_view.setOnRefreshListener(this);
        //数据为空才重新发起请求
        if (commonAdapter.getSize() == 0) {
            mStartPage = 0;
            mPresenter.listCommonContentRequest(null, cateCode, "", mStartPage, SIZE);
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
    public void returnlistCommonContent(InformationResponse<InformationEntity> informationResponse) {
        List<InformationEntity> informations = informationResponse.getDataList();
        totalPages = informationResponse.getTotalPage();
        if (informations != null && informations.size() > 0) {
            LogUtils.logd("returnlistCommonContent======" + informations.size());
            mStartPage += 1;
            if (commonAdapter.getPageBean().isRefresh()) {
                common_recycle_view.setRefreshing(false);
                commonAdapter.setData(informations);
            } else {
                if (informations.size() > 0) {
                    common_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    commonAdapter.addAll(informations);
                } else {
                    common_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        } else {
            loadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
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
    public void onTopicClick(int position, InformationEntity data) {
        if(data.getMediaType() == 0){
            MediaParamEntity info = new MediaParamEntity();
            info.setUrl(data.getUrl());
            info.setTitle(data.getName());
            info.setResId(data.getId());
            info.setResType(1);
            info.setCover(data.getIcon());
            info.setUserId(AppConfig.getInstance().getInt(AppConstant.USER_ID,-1));
            MediaPlayerActivity.startAction(getActivity(), info);
        }else{
            WebActivity.startAction(getActivity(),data.getId(),"常规学习");
        }
    }
}
