package com.hisu.smart.dj.ui.main.fragment;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.HomeItemBean;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.ui.adapter.HomeReaycleAdapter;
import com.hisu.smart.dj.ui.adapter.NewsRecyclerAdapter;
import com.hisu.smart.dj.ui.main.contract.NewsListContract;
import com.hisu.smart.dj.ui.main.model.NewsListModel;
import com.hisu.smart.dj.ui.main.presenter.NewsListPresenter;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.LogUtils;

import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.youth.banner.Banner;

import com.youth.banner.listener.OnBannerListener;


import java.util.ArrayList;
import java.util.List;



/**
 * 首页
 * @author lichee
 */
public class HomeFragment extends BaseFragment<NewsListPresenter, NewsListModel>
        implements NewsListContract.View , OnBannerListener
            ,HomeReaycleAdapter.OnItemClickListener,
        NewsRecyclerAdapter.OnNewsItemClickListener{
    private String TAG = "HomeFragment";
    private Banner homeBanner;
    private List<Integer> homeBannerImages;
    private RecyclerView mRecyclerView; //首页子栏目RecyclerView
    private RecyclerView homeNewRecyclerView;  //首页党建要闻RecyclerView
    private RecyclerView homeShizhRecyclerView;  //首页时政要闻RecyclerView

    private NewsRecyclerAdapter newsRecyclerAdapter; //党建要闻Adapter
    private NewsRecyclerAdapter shizhRecyclerAdapter;  //时政要闻Adapter

    List<InformationEntity> newsList = new ArrayList<>();  //党建要闻数据
    List<InformationEntity> shizhNewsList = new ArrayList<>(); //时政要闻数据
    private Context context;
    private HomeReaycleAdapter homeReaycleAdapter;
    private LoadingTip newTip;
    private LoadingTip shizhTip;
    private int[] recycleImages = {R.mipmap.news_icon,R.mipmap.vedio_icon,
                 R.mipmap.sanhyk_icon, R.mipmap.online_exam,
                 R.mipmap.jicengdt_icon,R.mipmap.df_pay_icon,
                 R.mipmap.dangyq_icon,R.mipmap.xianfeng_icon,
                 R.mipmap.zhibhd_icon,R.mipmap.group_icon };
    private String[] recycleStrings = {"党建资讯","视频大讲堂","三会一课","在线考试",
            "基层动态","党费缴纳","党员圈","时代先锋","支部活动","组织关系"};

    private List<HomeItemBean> dataList; //首页子栏目列表
    private int[] Images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};

    public HomeFragment() {

    }

    private void initData() {
        context = getActivity();
        homeBannerImages = new ArrayList<>();
        for(int i = 0; i < Images.length; i++){
            homeBannerImages.add(Images[i]);
        }

        dataList = new ArrayList<>();
        for(int i = 0; i < recycleStrings.length; i++){
            HomeItemBean homeItemBean = new HomeItemBean();
            homeItemBean.setItemImage(recycleImages[i]);
            homeItemBean.setItemName(recycleStrings[i]);
            dataList.add(homeItemBean);
        }

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }
    //在initView之前调用
    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
        initData();
    }

    @Override
    protected void initView() {

       homeBanner = rootView.findViewById(R.id.home_banner);
       homeBanner.setOnBannerListener(this);
       BannerWidget.setBanner(homeBanner,homeBannerImages);
       //首页栏目RecyclerView
       mRecyclerView = rootView.findViewById(R.id.home_recyclerView);
       //设置布局，禁止滑动
       GridLayoutManager gridLayoutManager = new GridLayoutManager(
                context,5,GridLayoutManager.VERTICAL,false){
           @Override
           public boolean canScrollHorizontally() {
               return false;
           }

           @Override
           public boolean canScrollVertically() {
               return false;
           }
       };
       mRecyclerView.setLayoutManager(gridLayoutManager);
       homeReaycleAdapter = new HomeReaycleAdapter(dataList);
       homeReaycleAdapter.setOnItemClickListener(this);
       mRecyclerView.setAdapter(homeReaycleAdapter);
       
       //首页党建要闻RecyclerView
       homeNewRecyclerView = rootView.findViewById(R.id.home_news_RecycleView);
        //设置布局，禁止滑动
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context){
           @Override
           public boolean canScrollHorizontally() {
               return false;
           }

           @Override
           public boolean canScrollVertically() {
               return false;
           }
       };
       homeNewRecyclerView.setLayoutManager(layoutManager);
       newsRecyclerAdapter = new NewsRecyclerAdapter(getActivity());
       newsRecyclerAdapter.setOnItemClickListener(this);
       homeNewRecyclerView.setAdapter(newsRecyclerAdapter);

       //时政要闻RecyclerView
       homeShizhRecyclerView = rootView.findViewById(R.id.home_shizh__RecycleView);
        //设置布局，禁止滑动
       RecyclerView.LayoutManager shizhlayoutManager = new LinearLayoutManager(context){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
       homeShizhRecyclerView.setLayoutManager(shizhlayoutManager);
       shizhRecyclerAdapter = new NewsRecyclerAdapter(getActivity());
       shizhRecyclerAdapter.setOnItemClickListener(this);
       homeShizhRecyclerView.setAdapter(shizhRecyclerAdapter);

       newTip = rootView.findViewById(R.id.loadedTip_new);
       shizhTip = rootView.findViewById(R.id.loadedTip_shizh);

       //有网络则请求数据
       if(AppApplication.isNet){
           //请求党建要闻
           mPresenter.getNewsListDataRequest("1003","",1,2);
           //请求时政要闻
           mPresenter.getNewsListDataRequest("1001","",1,2);
       }else{
           Toast.makeText(context,"网络异常，请检查网络",Toast.LENGTH_SHORT).show();
       }


    }
    //轮播图点击事件
    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(context,"item"+position,Toast.LENGTH_LONG).show();
    }
    //首页栏目列表点击事件
    @Override
    public void onClick(int position) {
        Toast.makeText(context,"item"+position,Toast.LENGTH_SHORT).show();
    }

    //首页新闻item点击事件
    @Override
    public void onNewsClick(int position) {
        Toast.makeText(context,"item"+position,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showLoading(String tag) {
        if(tag.equals("1003")){
            newTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }
        if(tag.equals("1001")){
            shizhTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }
    }

    @Override
    public void stopLoading(String tag) {
        if(tag.equals("1003")){
            newTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
        if(tag.equals("1001")){
            shizhTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
    }

    @Override
    public void showErrorTip(String msg,String tag) {
        if(tag.equals("1003")){
            newTip.setTips(msg);
        }
        if(tag.equals("1001")){
            shizhTip.setTips(msg);
        }
    }
    //首页新闻数据返回
    @Override
    public void returnNewsListData(List<InformationEntity> informations,String tag) {
        LogUtils.logd("returnNewsListData======================size=="+informations.size());
        LogUtils.logd("returnNewsListData======================"+informations);
        LogUtils.logd("returnNewsListData======================Tag==="+tag);
        if(informations != null && informations.size() > 0){

            if(tag!=null&&tag=="1003"&&tag.equals("1003")){
                newsList = informations;
                newsRecyclerAdapter.setData(newsList);
            }else if(tag!=null&&tag=="1001"&&tag.equals("1001")){
                shizhNewsList = informations;
                shizhRecyclerAdapter.setData(shizhNewsList);
            }
        }


    }

}
