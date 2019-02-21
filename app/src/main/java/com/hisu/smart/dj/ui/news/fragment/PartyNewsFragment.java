package com.hisu.smart.dj.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.ui.adapter.NewsRecyclerAdapter;

import com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity;
import com.hisu.smart.dj.ui.news.contract.PartyNewsContract;
import com.hisu.smart.dj.ui.news.model.PartyNewsModel;
import com.hisu.smart.dj.ui.news.presenter.PartyNewsPresenter;

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
public class PartyNewsFragment extends BaseFragment<PartyNewsPresenter, PartyNewsModel> implements PartyNewsContract.View, OnLoadMoreListener, OnRefreshListener, NewsRecyclerAdapter.OnNewsItemClickListener {

    @Bind(R.id.party_news_banner)
    Banner commonBanner;
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
    NewsRecyclerAdapter commonAdapter;
    private static int SIZE = 6;
    private int mStartPage = 1;
    private final static String party_cateCode = "100511"; //制度文件-》党办
    private boolean isFile;
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
        BannerWidget.setBanner(commonBanner, bannerImages);
        if (getArguments() != null) {
            cateCode = getArguments().getString(AppConstant.COMMON_CATE_CODE);
            isFile = getArguments().getBoolean("ISFILE",false);
        }
        commonAdapter = new NewsRecyclerAdapter(getActivity());
        commonAdapter.setOnItemClickListener(this);
        party_news_recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));
        party_news_recycle_view.setAdapter(commonAdapter);
        party_news_recycle_view.setOnLoadMoreListener(this);
        party_news_recycle_view.setOnRefreshListener(this);
        //数据为空才重新发起请求
        if (commonAdapter.getSize() == 0) {
            mStartPage = 0;
            if(isFile){
                mPresenter.listInformationRequest(null, party_cateCode, "", mStartPage, SIZE);
            }else{
                mPresenter.listInformationRequest(null, cateCode, "", mStartPage, SIZE);

            }
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

    @Override
    public void returnlistInformation(InformationResponse<InformationEntity> informationResponse) {
        List<InformationEntity> informations = informationResponse.getDataList();
        totalPages = informationResponse.getTotalPage();
        if (informations != null && informations.size() > 0) {
            LogUtils.logd("returnlistCommonContent======" + informations.size());
            mStartPage += 1;
            if (commonAdapter.getPageBean().isRefresh()) {
                party_news_recycle_view.setRefreshing(false);
                commonAdapter.setData(informations);
            } else {
                if (informations.size() > 0) {
                    party_news_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    commonAdapter.addAll(informations);
                } else {
                    party_news_recycle_view.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
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
        if(data.getMediaType() == 0){
            MediaPlayerActivity.startAction(getActivity(),data);
        }else{
            WebActivity.startAction(getActivity(),data.getId());
        }

    }
}
