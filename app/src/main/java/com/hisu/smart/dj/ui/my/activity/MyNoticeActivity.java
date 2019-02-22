package com.hisu.smart.dj.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.ui.adapter.NoticeRecyclerAdapter;
import com.hisu.smart.dj.ui.my.contract.NoticeContract;
import com.hisu.smart.dj.ui.my.model.NoticeModel;
import com.hisu.smart.dj.ui.my.presenter.NoticePresenter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.NetWorkUtils;
import com.jaydenxiao.common.commonwidget.LoadingDialog;

import java.util.List;

import butterknife.Bind;

public class MyNoticeActivity extends BaseActivity<NoticePresenter,NoticeModel> implements
        View.OnClickListener,OnRefreshListener,OnLoadMoreListener,NoticeContract.View,
        NoticeRecyclerAdapter.OnNoticeItemClickListener{
    @Bind(R.id.title_TextView)
    TextView title_Tv;
    @Bind(R.id.back_imageView)
    ImageView back_img;
    @Bind(R.id.my_notice_recycle_view)
    IRecyclerView noticeIRecyclerView;
    private static int SIZE = 20;
    private NoticeRecyclerAdapter noticeRecyclerAdapter;
    private String lastDateTime;
    private int lastDateId;
    private int dateSize = 0;
    private int userId;
    private int partyBranchId;
    private boolean isFirst = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_notice;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
        userId = AppConfig.getInstance().getInt(AppConstant.USER_ID,-1);
        partyBranchId = AppConfig.getInstance().getInt(AppConstant.MEMBER_PARTYBRANCH_ID,-1);
        noticeRecyclerAdapter = new NoticeRecyclerAdapter(this);
        noticeRecyclerAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(NetWorkUtils.isNetConnected(AppApplication.getAppContext())){
            isFirst = true;
            mPresenter.getListNoticeByTimeRequest(userId,partyBranchId,null,null,SIZE);
        }else{
            Toast.makeText(this,"网络异常!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void initView() {
        title_Tv.setText("消息");
        back_img.setOnClickListener(this);

        noticeIRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticeIRecyclerView.setAdapter(noticeRecyclerAdapter);
        noticeIRecyclerView.setOnLoadMoreListener(this);
        noticeIRecyclerView.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imageView:
                MyNoticeActivity.this.finish();
                break;
        }
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        noticeRecyclerAdapter.getPageBean().setRefresh(false);
        isFirst = false;
        //发起请求
        if(dateSize == 20){
            mPresenter.getListNoticeByTimeRequest(userId,partyBranchId,lastDateId,lastDateTime,SIZE);
            noticeIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        }else{
            noticeIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    @Override
    public void onRefresh() {
        isFirst = false;
        noticeRecyclerAdapter.getPageBean().setRefresh(true);
        //发起请求
        noticeIRecyclerView.setRefreshing(true);
        mPresenter.getListNoticeByTimeRequest(userId,partyBranchId,null,null,SIZE);
        noticeIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
    }
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, MyNoticeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void returnListNoticeByTime(NoticeInfoEntity noticeInfoEntity, String tag) {
        List<NoticeInfoEntity.DataListBean> informations = noticeInfoEntity.getDataList();
        Log.d("MyNoticeActivity","informations--size()===="+informations.size());
        if(informations != null && informations.size()>0){
            dateSize = informations.size();
            lastDateId = informations.get(dateSize-1).getId();
            lastDateTime = informations.get(dateSize-1).getPublishTime();
            if (noticeRecyclerAdapter.getPageBean().isRefresh()) {
                Log.d("MyNoticeActivity","informations--size()===="+informations.size());
                noticeIRecyclerView.setRefreshing(false);
                noticeRecyclerAdapter.setData(informations);
            } else {
                if (informations.size() > 0) {
                    noticeIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    noticeRecyclerAdapter.addAll(informations);
                } else {
                    noticeIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void showLoading(String tag) {
        if(isFirst){
            LoadingDialog.cancelDialogForLoading();
        }

    }

    @Override
    public void stopLoading(String tag) {
       if(isFirst){
           LoadingDialog.cancelDialogForLoading();
       }
    }

    @Override
    public void showErrorTip(String msg, String tag) {
      if(isFirst){
          LoadingDialog.cancelDialogForLoading();
      }
      Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoticeClick(int position, NoticeInfoEntity.DataListBean noticeBean) {
        NoticeInfoActivity.startAction(this,noticeBean);
    }
}
