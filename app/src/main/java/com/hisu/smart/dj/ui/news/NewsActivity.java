package com.hisu.smart.dj.ui.news;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.ui.adapter.NewsRecyclerAdapter;
import com.hisu.smart.dj.ui.main.contract.NewsListContract;
import com.hisu.smart.dj.ui.main.model.NewsListModel;
import com.hisu.smart.dj.ui.main.presenter.NewsListPresenter;
import com.hisu.smart.dj.ui.study.activity.StudyTopicActivity;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class NewsActivity extends BaseActivity<NewsListPresenter,NewsListModel>
        implements NewsListContract.View, View.OnClickListener,OnBannerListener,
        NewsRecyclerAdapter.OnNewsItemClickListener,OnRefreshListener,OnLoadMoreListener{

    @Bind(R.id.title_TextView)
    TextView title;
    @Bind(R.id.back_imageView)
    ImageView back_img;
    @Bind(R.id.news_banner)
    Banner newsBanner;
    @Bind(R.id.news_recycle_view)
    IRecyclerView newsIRecyclerView;
    @Bind(R.id.news_loadedTip)
    LoadingTip news_LoadTip;
    private static final String TAG = "NewsActivity";
    private List<Integer> newsBannerImages;
    private int[] images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};

    private static final String PARTY_NEWS = "1003"; //党建要闻
    private static final String SHIZ_NEWS = "1001"; //时政要闻
    private static int SIZE = 6;
    private int mStartPage = 1;

    private String show_title;
    private NewsRecyclerAdapter newsRecyclerAdapter;
    private boolean is_party_news;
    private int totalPages ;


    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
        newsRecyclerAdapter = new NewsRecyclerAdapter(this);
        newsRecyclerAdapter.setOnItemClickListener(this);
        initData();
    }

    private void initData() {
        show_title = this.getIntent().getStringExtra(AppConstant.SHOW_TITLE);
        if(show_title!=null&&show_title.equals("党建要闻")){
            is_party_news = true;
        }else{
            is_party_news = false;
        }
        newsBannerImages = new ArrayList<>();
        for(int i = 0; i < images.length; i++){
            newsBannerImages.add(images[i]);
        }
    }

    @Override
    public void initView() {
        title.setText(show_title);
        back_img.setOnClickListener(this);
        newsBanner.setOnBannerListener(this);
        BannerWidget.setBanner(newsBanner,newsBannerImages);

        newsIRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsIRecyclerView.setAdapter(newsRecyclerAdapter);
        newsIRecyclerView.setOnLoadMoreListener(this);
        newsIRecyclerView.setOnRefreshListener(this);
        if(AppApplication.isNet){
            if(is_party_news){
                mPresenter.getNewsListDataRequest(PARTY_NEWS,"",mStartPage,SIZE);
            }else{
                mPresenter.getNewsListDataRequest(SHIZ_NEWS,"",mStartPage,SIZE);
            }
        }else{
            Toast.makeText(this,"网络异常!",Toast.LENGTH_LONG).show();
        }
    }

    public static void startAction(Activity activity, String title){
        Intent intent = new Intent(activity, NewsActivity.class);
        intent.putExtra(AppConstant.SHOW_TITLE,title);
        activity.startActivity(intent);
    }

    @Override
    public void returnNewsListData(InformationResponse informationResponse, String tag) {
        List<InformationEntity> informations = informationResponse.getDataList();
        totalPages = informationResponse.getTotalPage();
        if(informations != null){
            mStartPage +=1;
            if (newsRecyclerAdapter.getPageBean().isRefresh()) {
                newsIRecyclerView.setRefreshing(false);
                newsRecyclerAdapter.setData(informations);
            } else {
                if (informations.size() > 0) {
                    newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    newsRecyclerAdapter.addAll(informations);
                } else {
                    newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void showLoading(String tag) {
       news_LoadTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading(String tag) {
        news_LoadTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        if( newsRecyclerAdapter.getPageBean().isRefresh()) {
            if(newsRecyclerAdapter.getSize()<=0) {
                news_LoadTip.setLoadingTip(LoadingTip.LoadStatus.error);
                news_LoadTip.setTips(msg);
            }
            newsIRecyclerView.setRefreshing(false);
        }else{
            newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.back_imageView:
               NewsActivity.this.finish();
               break;
       }
    }
    //Banner图点击事件
    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void onNewsClick(int position,int news_id) {
        Log.d(TAG,"----item----position----"+position);
       Toast.makeText(this,"item-id=="+news_id,
               Toast.LENGTH_LONG).show();
        WebActivity.startAction(this,news_id);
    }
    //加载
    @Override
    public void onLoadMore(View loadMoreView) {
        newsRecyclerAdapter.getPageBean().setRefresh(false);
        //发起请求
        if(totalPages >= mStartPage){
            if(is_party_news){
                mPresenter.getNewsListDataRequest(PARTY_NEWS,"",mStartPage,SIZE);
            }else{
                mPresenter.getNewsListDataRequest(SHIZ_NEWS,"",mStartPage,SIZE);
            }
            newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        }else{
            newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        newsRecyclerAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        newsIRecyclerView.setRefreshing(true);
        if(is_party_news){
            mPresenter.getNewsListDataRequest(PARTY_NEWS,"",mStartPage,SIZE);
        }else{
            mPresenter.getNewsListDataRequest(SHIZ_NEWS,"",mStartPage,SIZE);
        }
        newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
    }
}
