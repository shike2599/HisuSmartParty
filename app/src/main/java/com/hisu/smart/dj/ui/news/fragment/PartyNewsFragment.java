package com.hisu.smart.dj.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
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
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.adapter.NewsRecyclerAdapter;

import com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity;
import com.hisu.smart.dj.ui.news.contract.PartyNewsContract;
import com.hisu.smart.dj.ui.news.model.PartyNewsModel;
import com.hisu.smart.dj.ui.news.presenter.PartyNewsPresenter;

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
public class PartyNewsFragment extends BaseFragment<PartyNewsPresenter, PartyNewsModel> implements PartyNewsContract.View, OnLoadMoreListener, OnRefreshListener, NewsRecyclerAdapter.OnNewsItemClickListener {

    @Bind(R.id.party_news_recycle_view)
    IRecyclerView party_news_recycle_view;
    @Bind(R.id.party_news_loadedTip)
    LoadingTip loadingTip;
    private int totalPages;
    private static String TAG = "PartyNewsFragment";
    private List<Integer> bannerImages;
    private int[] images = {
            R.mipmap.home_banner_1, R.mipmap.home_banner_1,
            R.mipmap.home_banner_1, R.mipmap.home_banner_1};
    private String cateCode;
    private Banner commonBanner;
    private NewsRecyclerAdapter commonAdapter;
    private static int SIZE = 6;
    private int mStartPage = 1;
    private final static String party_cateCode = "100511"; //制度文件-》党办
    private boolean isFile;
    private int resId;

    public PartyNewsFragment() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_party_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
        initData();
    }

    @Override
    protected void initView() {
        commonBanner = (Banner) LayoutInflater.from(getActivity()).inflate(R.layout.layout_banner_view, party_news_recycle_view.getHeaderContainer(), false);
        if (getArguments() != null) {
            cateCode = getArguments().getString(AppConstant.COMMON_CATE_CODE);
            isFile = getArguments().getBoolean("ISFILE", false);
        }
        commonAdapter = new NewsRecyclerAdapter(getActivity());
        commonAdapter.setOnItemClickListener(this);
        party_news_recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));
        party_news_recycle_view.removeHeaderAllView();
        party_news_recycle_view.addHeaderView(commonBanner);
        BannerWidget.setBanner(commonBanner, bannerImages);
        party_news_recycle_view.setAdapter(commonAdapter);
        party_news_recycle_view.setOnLoadMoreListener(this);
        party_news_recycle_view.setOnRefreshListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        //数据为空才重新发起请求
        if (commonAdapter.getSize() == 0) {
            mStartPage = 0;
            if (isFile) {
                mPresenter.listInformationRequest(null, party_cateCode, "", mStartPage, SIZE);
            } else {
                mPresenter.listInformationRequest(null, cateCode, "", mStartPage, SIZE);

            }
        } else {
            mPresenter.getResVisitNumRequest(0, resId);
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
            party_news_recycle_view.setRefreshing(false);
        } else {
            party_news_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    private List<InformationEntity> informations;

    @Override
    public void returnlistInformation(InformationResponse<InformationEntity> informationResponse) {
        informations = informationResponse.getDataList();
        totalPages = informationResponse.getTotalPage();
        if (informations != null) {
            LogUtils.logd("returnlistCommonContent======" + informations.size());
            mStartPage += 1;
            if (commonAdapter.getPageBean().isRefresh()) {
                party_news_recycle_view.setRefreshing(false);
                int size = informations.size();
                String ids = "";
                for (int i = 0; i < size; i++) {
                    ids += informations.get(i).getId() + ",";
                }
                if (ids.endsWith(",")) {
                    ids = ids.substring(0, ids.lastIndexOf(","));
                }
                mPresenter.getAllResVisitNumRequest("1", 0, ids);
//                commonAdapter.setData(informations);
            } else {
                if (informations.size() > 0) {
                    party_news_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    int size = informations.size();
                    String ids = "";
                    for (int i = 0; i < size; i++) {
                        ids += informations.get(i).getId() + ",";
                    }
                    if (ids.endsWith(",")) {
                        ids = ids.substring(0, ids.lastIndexOf(","));
                    }
                    mPresenter.getAllResVisitNumRequest("2", 0, ids);
//                    commonAdapter.addAll(informations);
                } else {
                    party_news_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        } else {
            loadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
        }

    }


    @Override
    public void returnResVisitNum(VisitNumResponse visitNumResponse) {
        int num = visitNumResponse.getData();
        List<InformationEntity> informationList = commonAdapter.getData();
        if (informationList != null) {
            for (InformationEntity entity : informationList) {
                if (entity.getId() == resId) {
                    entity.setWatchNum(num);
                }
            }
        }
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnAddResVisitNum(BaseResponse response) {
        Log.i(TAG, "returnAddResVisitNum=============================" + response.getResultCode());
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
                        InformationEntity informationEntity = informations.get(j);
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
                        InformationEntity informationEntity = informations.get(j);
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
        Log.d("page", "totalpage=" + totalPages + ",startPage=" + mStartPage);
        if (totalPages > mStartPage) {
            party_news_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
            mPresenter.listInformationRequest(null, cateCode, "", mStartPage, SIZE);
        } else {
            party_news_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    @Override
    public void onRefresh() {
        commonAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        party_news_recycle_view.setRefreshing(true);
        mPresenter.listInformationRequest(null, cateCode, "", mStartPage, SIZE);
        party_news_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
    }

    @Override
    public void onNewsClick(int position, InformationEntity data) {
        resId = data.getId();
        mPresenter.getAddResVisitNumRequest(0, data.getId());
        if (data.getMediaType() == 0) {
            MediaParamEntity info = new MediaParamEntity();
            info.setUrl(data.getUrl());
            info.setTitle(data.getName());
            info.setResId(data.getId());
            info.setResType(0);
            info.setCover(data.getIcon());
            info.setUserId(AppConfig.getInstance().getInt(AppConstant.USER_ID, -1));
            info.setCreateTime(data.getPublishTime());
            MediaPlayerActivity.startAction(getActivity(), info);
        } else {
            WebActivity.startAction(getActivity(), data.getId());
        }

    }
}
