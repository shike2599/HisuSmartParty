package com.hisu.smart.dj.ui.main.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.HomeItemBean;
import com.hisu.smart.dj.ui.adapter.HomeReaycleAdapter;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 首页
 * @author lichee
 */
public class HomeFragment extends BaseFragment implements OnBannerListener
            ,HomeReaycleAdapter.OnItemClickListener{
    private String TAG = "HomeFragment";
    private Banner homeBanner;
    private List<Integer> homeBannerImages;
    private RecyclerView mRecyclerView;
    private RecyclerView homeNewRecyclerView;
    private Context context;
    private HomeReaycleAdapter homeReaycleAdapter;
    private int[] recycleImages = {R.mipmap.news_icon,R.mipmap.vedio_icon,
                 R.mipmap.sanhyk_icon, R.mipmap.online_exam,
                 R.mipmap.jicengdt_icon,R.mipmap.df_pay_icon,
                 R.mipmap.dangyq_icon,R.mipmap.xianfeng_icon,
                 R.mipmap.zhibhd_icon,R.mipmap.group_icon };
    private String[] recycleStrings = {"党建资讯","视频大讲堂","三会一课","在线考试",
            "基层动态","党费缴纳","党员圈","时代先锋","支部活动","组织关系"};

    private List<HomeItemBean> dataList;
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
        initData();
    }

    @Override
    protected void initView() {

       homeBanner = rootView.findViewById(R.id.home_banner);
       homeBanner.setOnBannerListener(this);
       BannerWidget.setBanner(homeBanner,homeBannerImages);
       //首页栏目RecyclerView
       mRecyclerView = rootView.findViewById(R.id.home_recyclerView);
       GridLayoutManager gridLayoutManager = new GridLayoutManager(
                context,5,GridLayoutManager.VERTICAL,false);
       mRecyclerView.setLayoutManager(gridLayoutManager);
       homeReaycleAdapter = new HomeReaycleAdapter(dataList);
       homeReaycleAdapter.setOnItemClickListener(this);
       mRecyclerView.setAdapter(homeReaycleAdapter);
       //首页党建要闻RecyclerView
       homeNewRecyclerView = rootView.findViewById(R.id.home_news_RecycleView);
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
       homeNewRecyclerView.setLayoutManager(layoutManager);
//        Api.getDefault(context, AppConstant.HOST_URL).
//                listInformation("1003",null,1,2).

    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(context,"item"+position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(context,"item"+position,Toast.LENGTH_SHORT).show();
    }
}
