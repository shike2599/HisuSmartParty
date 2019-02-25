package com.hisu.smart.dj.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.hisu.smart.dj.entity.CollectEntity;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.MediaParamEntity;
import com.hisu.smart.dj.ui.adapter.CollectRecyclerAdapter;
import com.hisu.smart.dj.ui.my.contract.MyCollectContract;
import com.hisu.smart.dj.ui.my.model.MyCollectModel;
import com.hisu.smart.dj.ui.my.presenter.MyCollectPresenter;
import com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import java.util.List;

import butterknife.Bind;


public class MyCollectActivity extends BaseActivity<MyCollectPresenter, MyCollectModel> implements MyCollectContract.View, OnLoadMoreListener, OnRefreshListener, CollectRecyclerAdapter.OnCollectItemClickListener {


    @Bind(R.id.title_TextView)
    TextView titleView;
    @Bind(R.id.back_imageView)
    ImageView imageView;
    @Bind(R.id.my_collect_recycle_view)
    IRecyclerView collectRecycle;
    @Bind(R.id.my_collect_loadedTip)
    LoadingTip loadingTip;

    private int user_id = 0;
    private int cid = 0;

    private CollectRecyclerAdapter collectRecyclerAdapter;

    private final static String TAG = "MyCollectActivity";

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MyCollectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        titleView.setText("收藏");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collectRecyclerAdapter = new CollectRecyclerAdapter(this);
        collectRecycle.setLayoutManager(new LinearLayoutManager(this));
        collectRecycle.setAdapter(collectRecyclerAdapter);
        collectRecycle.setOnLoadMoreListener(this);
        collectRecycle.setOnRefreshListener(this);
        collectRecyclerAdapter.setOnItemClickListener(this);
        user_id = AppConfig.getInstance().getInt(AppConstant.USER_ID, -1);
        //数据为空才重新发起请求
        if (collectRecyclerAdapter.getSize() == 0) {
            mPresenter.getCollectListDataRequest(user_id, null, 10);
        }
    }

    @Override
    public void showLoading(String tag) {
        if (collectRecyclerAdapter.getPageBean().isRefresh()) {
            if (collectRecyclerAdapter.getSize() <= 0) {
                loadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading(String msg) {
        if ("".equals(msg)) {
            loadingTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        } else {
            cid = -1;
            collectRecycle.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        if (collectRecyclerAdapter.getPageBean().isRefresh()) {
            if (collectRecyclerAdapter.getSize() <= 0) {
                loadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadingTip.setTips(msg);
            }
            collectRecycle.setRefreshing(false);
        } else {
            collectRecycle.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void returnCollectListData(List<CollectEntity> collectList) {
        if (collectRecyclerAdapter.getPageBean().isRefresh()) {
            collectRecycle.setRefreshing(false);
            collectRecyclerAdapter.setData(collectList);
            int size = collectList.size();
            cid = collectList.get(size - 1).getId();
            collectRecycle.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        } else {
            collectRecycle.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
            collectRecyclerAdapter.addAll(collectList);
            cid = collectList.get(collectList.size() - 1).getId();
        }
    }


    @Override
    public void onRefresh() {
        collectRecyclerAdapter.getPageBean().setRefresh(true);
        //发起请求
        collectRecycle.setRefreshing(true);
        mPresenter.getCollectListDataRequest(user_id, null, 10);
        collectRecycle.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        collectRecyclerAdapter.getPageBean().setRefresh(false);
        if (cid > 0) {
            collectRecycle.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
            mPresenter.getCollectListDataRequest(user_id, cid, 10);
        } else {
            collectRecycle.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    @Override
    public void onCollectClick(int position, CollectEntity data) {
        if (data.getMediaType() == 0) {
            MediaParamEntity info = new MediaParamEntity();
            info.setUrl(data.getUrl());
            info.setTitle(data.getName());
            info.setResId(data.getResId());
            info.setResType(data.getResId());
            info.setUserId(AppConfig.getInstance().getInt(AppConstant.USER_ID,-1));
            info.setCreateTime(data.getCreateTime());
            MediaPlayerActivity.startAction(this, info);
        } else {
            int resType = data.getResType();
            String jump_tag = null;
            if(resType == 1){
                jump_tag = "常规学习";
            }else if(resType == 2){
                jump_tag = "专题学习";
            }else if(resType == 3){
                jump_tag = "践行活动";
            }
            WebActivity.startAction(this, data.getResId(),jump_tag);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
