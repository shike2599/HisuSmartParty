package com.hisu.smart.dj.ui.main.fragment;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.GridViewItemEntity;
import com.hisu.smart.dj.ui.adapter.GridViewAdapter;
import com.hisu.smart.dj.ui.news.activity.NewsActivity;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.hisu.smart.dj.ui.widget.MyGridView;
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
    private static final String PARTY_BUILD = "partyBuild/";
    //党委简介
    private static String partyBuild_partyCommitContro = AppConstant.BASE_URL_LOAD+PARTY_BUILD+"partyCommitContro.html";
    //支部简介
    private static String partyBuild_partyBranChIntro = AppConstant.BASE_URL_LOAD+PARTY_BUILD+"partyBranChIntro.html";
    //组织关系
    private static String partyBuild_relation = AppConstant.BASE_URL_LOAD+PARTY_BUILD+"relation.html";
    //党费缴纳
    private static String partyBuild_payCost = AppConstant.BASE_URL_LOAD+PARTY_BUILD+"payCost.html";
    //党组架构
    private static String partyBuild_partyStructure = AppConstant.BASE_URL_LOAD+PARTY_BUILD+"partyStructure.html";
    //党建大数据
    private static String partyBuild_bigData = AppConstant.BASE_URL_LOAD+PARTY_BUILD+"bigData.html";

    private Banner partyBanner;
    private List<Integer> partyBannerImages;
    private int[] Images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};
    private MyGridView gridView;
    private boolean isPartyBranch;
    private boolean isPartyCommittee;
    private List<GridViewItemEntity> gridViewItemEntities;
    private GridViewAdapter gridViewAdapter ;
    private NestedScrollView nestedScrollView;
    public PartyBuildFragment() {
    }


    private void initBannerData() {
        partyBannerImages = new ArrayList<>();
        isPartyBranch = AppConfig.getInstance().getBoolean(AppConstant.IS_PARTY_BRANCH,false);
        isPartyCommittee = AppConfig.getInstance().getBoolean(AppConstant.IS_PARTY_COMMITTEE,false);
        for(int i = 0; i < Images.length; i++){
            partyBannerImages.add(Images[i]);
        }
    }
    private void initGridViewData() {
        gridViewItemEntities = new ArrayList<>();
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.dang_info,"党委简介","方便党员了解上级党委信息"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.relation_group,"组织关系","组织关系在线转接"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.part_info,"支部简介","方便党员了解所在支部信息"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.online_pay,"党费缴纳","提供党员在线缴纳党费"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.dang_task,"党务公开","方便党员及时党建情况"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.dang_xmind,"党组架构","党组织及党员信息查看"));
        gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.dang_events,"支部活动","支部活动实时了解及参与"));
        //普通党员账号不显示党建大数据栏目
        if(isPartyBranch || isPartyCommittee){
            gridViewItemEntities.add(new GridViewItemEntity(R.mipmap.big_data,"党建大数据","辅助决策，提升党的管理"));
        }
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
        nestedScrollView = rootView.findViewById(R.id.party_build_ScrollView);
        BannerWidget.setBanner(partyBanner,partyBannerImages);
        gridView = rootView.findViewById(R.id.party_gridview);
        nestedScrollView.requestFocus();
        gridView.setFocusable(false);
        gridViewAdapter = new GridViewAdapter(getActivity());
        gridViewAdapter.setGridViewItemEntities(gridViewItemEntities);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title_TextView =  view.findViewById(R.id.item_title);
                String title = title_TextView.getText().toString();
                if(title=="党委简介"){
                    WebActivity.startAction(getActivity(),"党委简介",partyBuild_partyCommitContro);
                }else if(title=="组织关系"){
                    WebActivity.startAction(getActivity(),"组织关系",partyBuild_relation);
                }else if(title=="支部简介"){
                    WebActivity.startAction(getActivity(),"支部简介",partyBuild_partyBranChIntro);
                }else if(title=="党费缴纳"){
                    WebActivity.startAction(getActivity(),"党费缴纳",partyBuild_payCost);
                }else if(title=="党务公开"){
                    NewsActivity.startAction(getActivity(),"党务公开");
                }else if(title=="党组架构"){
                    WebActivity.startAction(getActivity(),"党组架构",partyBuild_partyStructure);
                }else if(title=="支部活动"){
                    NewsActivity.startAction(getActivity(),"支部活动");
                }else if(title=="党建大数据"){
                    WebActivity.startAction(getActivity(),"党建大数据",partyBuild_bigData);
                }
            }
        });
    }

}
