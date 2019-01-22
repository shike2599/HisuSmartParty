package com.hisu.smart.dj.ui.main.fragment;

import android.widget.GridView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.GridViewItemEntity;
import com.hisu.smart.dj.ui.main.adapter.GridViewAdapter;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseFragment;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


/**
 * 党建
 * @author lichee
 */
public class PartyBuildFragment extends BaseFragment {

    private static final String TAG = "PartyBuildFragment";
    private Banner partyBanner;
    private List<Integer> partyBannerImages;
    private int[] Images = {
            R.mipmap.home_selected_ico,R.mipmap.study_selected_icon,
            R.mipmap.dang_selected_icon,R.mipmap.doit_selected_icon};
    private GridView gridView;

    private List<GridViewItemEntity> gridViewItemEntities;
    private GridViewAdapter gridViewAdapter ;
    public PartyBuildFragment() {
    }


    private void initBannerData() {
        partyBannerImages = new ArrayList<>();
        for(int i = 0; i < Images.length; i++){
            partyBannerImages.add(Images[i]);
        }
    }
    private void initGridViewData() {
        gridViewItemEntities = new ArrayList<>();
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"党委简介","方便党员了解上级党委信息"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"组织关系","组织关系在线转接"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"支部简介","方便党员了解所在支部信息"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"党费缴纳","提供党员在线缴纳党费"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"党务公开","方便党员及时党建情况"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"党组架构","党组织及党员信息查看"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"支部活动","支部活动实时了解及参与"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.ic_launcher,"党建大数据","辅助决策，提升党的管理"));
    }


    private void initData() {
        initBannerData();
        initGridViewData();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_party_building;
    }

    @Override
    public void initPresenter() {
        initData();
    }

    @Override
    protected void initView() {
        partyBanner = rootView.findViewById(R.id.party_build_banner);
        BannerWidget.setBanner(partyBanner,partyBannerImages);
        gridView = rootView.findViewById(R.id.party_gridview);
        gridViewAdapter = new GridViewAdapter(getActivity());
        gridViewAdapter.setGridViewItemEntities(gridViewItemEntities);
        gridView.setAdapter(gridViewAdapter);
    }

}
