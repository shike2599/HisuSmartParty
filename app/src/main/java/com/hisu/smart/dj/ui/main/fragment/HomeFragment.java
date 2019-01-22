package com.hisu.smart.dj.ui.main.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 * @author lichee
 */
public class HomeFragment extends BaseFragment {
    private String TAG = "HomeFragment";
    private Banner homeBanner;
    private List<Integer> homeBannerImages;
    private int[] Images = {
            R.mipmap.home_selected_ico,R.mipmap.study_selected_icon,
            R.mipmap.dang_selected_icon,R.mipmap.doit_selected_icon};
    public HomeFragment() {

    }


    private void initData() {
        homeBannerImages = new ArrayList<>();
        for(int i = 0; i < Images.length; i++){
            homeBannerImages.add(Images[i]);
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
       BannerWidget.setBanner(homeBanner,homeBannerImages);
    }
}
