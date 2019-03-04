package com.hisu.smart.dj.ui.main.fragment;


import android.content.Context;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hisu.smart.dj.R;

import com.hisu.smart.dj.app.AppApplication;

import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.HomeItemBean;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.MediaParamEntity;
import com.hisu.smart.dj.entity.NoticeInfoEntity;

import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.ui.adapter.HomeReaycleAdapter;
import com.hisu.smart.dj.ui.adapter.NewsRecyclerAdapter;
import com.hisu.smart.dj.ui.iactive.activity.IactiveLoginActivity;
import com.hisu.smart.dj.ui.main.contract.HomeInfoContract;
import com.hisu.smart.dj.ui.main.model.HomeInfoModel;
import com.hisu.smart.dj.ui.main.presenter.HomeInfoPresenter;
import com.hisu.smart.dj.ui.my.activity.MyNoticeActivity;
import com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity;
import com.hisu.smart.dj.ui.news.activity.NewsActivity;
import com.hisu.smart.dj.ui.news.activity.PartyNewsActivity;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.commonutils.LogUtils;

import com.jaydenxiao.common.commonutils.NetWorkUtils;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.youth.banner.Banner;

import com.youth.banner.listener.OnBannerListener;


import java.util.ArrayList;
import java.util.List;



/**
 * 首页
 * @author lichee
 */
public class HomeFragment extends BaseFragment<HomeInfoPresenter, HomeInfoModel>
        implements HomeInfoContract.View , OnBannerListener,
        View.OnClickListener,HomeReaycleAdapter.OnItemClickListener,
        NewsRecyclerAdapter.OnNewsItemClickListener{
    private String TAG = "HomeFragment";

    private static String party_news = AppConstant.BASE_URL_LOAD+"news/newsList.html"; //党建资讯
    private static String partyMembersCircle = AppConstant.BASE_URL_LOAD+"partyMembersCircle.html"; //党员圈
    private static String payCost = AppConstant.BASE_URL_LOAD+"partyBuild/payCost.html"; //党费缴纳
    private static String newsMore = AppConstant.BASE_URL_LOAD+"news/newsMore.html"; //更多党建新闻
    private static String studyExamination = AppConstant.BASE_URL_LOAD+"study/studyExamination.html"; //在线考试
    private static String partyBuild_relation = AppConstant.BASE_URL_LOAD+"partyBuild/relation.html";

    private static String mymessage = AppConstant.BASE_URL_LOAD+"mycenter/"+"mymessage.html";

    private Banner homeBanner;
    private List<Integer> homeBannerImages;
    private RecyclerView mRecyclerView; //首页子栏目RecyclerView
    private RecyclerView homeNewRecyclerView;  //首页党建要闻RecyclerView
    private RecyclerView homeShizhRecyclerView;  //首页时政要闻RecyclerView
    private TextView noReadSizeText;
    private TextView noticeMsgTv;
    private NewsRecyclerAdapter newsRecyclerAdapter; //党建要闻Adapter
    private NewsRecyclerAdapter shizhRecyclerAdapter;  //时政要闻Adapter

    List<InformationEntity> newsList = new ArrayList<>();  //党建要闻数据
    List<InformationEntity> shizhNewsList = new ArrayList<>(); //时政要闻数据
    private Context context;
    private HomeReaycleAdapter homeReaycleAdapter;
    private LoadingTip newTip;
    private LoadingTip shizhTip;
    private TextView more_news_textView,more_shiznew_textView;
    private RelativeLayout notive_rela;
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
    private int user_id;
    private int partyMemberId;
    private int partyBranchId;
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
        user_id = AppConfig.getInstance().getInt(AppConstant.USER_ID,-1);
        partyMemberId = AppConfig.getInstance().getInt(AppConstant.MEMBER_ID,-1);
        partyBranchId = AppConfig.getInstance().getInt(AppConstant.MEMBER_PARTYBRANCH_ID,-1);
        initData();
    }

    @Override
    protected void initView() {
       more_news_textView = rootView.findViewById(R.id.more_news_textView);
       more_shiznew_textView = rootView.findViewById(R.id.shizh_more_news_textView);
       notive_rela = rootView.findViewById(R.id.home_notice_RelativeLayout);
       noReadSizeText = rootView.findViewById(R.id.show_no_read_message_textView);
       noticeMsgTv = rootView.findViewById(R.id.show_message_textView);

       notive_rela.setOnClickListener(this);
       more_news_textView.setOnClickListener(this);
       more_shiznew_textView.setOnClickListener(this);
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


    }

    @Override
    public void onResume() {
        super.onResume();
        //有网络则请求数据
        if(NetWorkUtils.isNetConnected(AppApplication.getAppContext())){
            //未读消息获取
            mPresenter.getUnReadNoticeNuRequest(user_id,partyMemberId);
            //获取最新消息
            mPresenter.getListNoticeByTimeRequest(user_id,partyBranchId,null,null,1);
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
//        Toast.makeText(context,"item"+position,Toast.LENGTH_SHORT).show();
        if(position == 0){
            PartyNewsActivity.startAction(getActivity());
        }else if(position == 1){
            IactiveLoginActivity.startAction(getActivity(),0);
        }else if(position == 2){
            NewsActivity.startAction(getActivity(),"三会一课");
        }else if(position == 3){
            WebActivity.startAction(getActivity(),"在线考试",studyExamination);
        }else if(position == 4){
            NewsActivity.startAction(getActivity(),"基层动态");
        }else if(position == 5){
            WebActivity.startAction(getActivity(),"党费缴纳",payCost);
        }else if(position == 6){
            WebActivity.startAction(getActivity(),"党员圈",partyMembersCircle);
        }else if(position == 7){
            NewsActivity.startAction(getActivity(),"时代先锋");
        }else if(position == 8){
           NewsActivity.startAction(getActivity(),"支部活动");
        }else if(position == 9){
            WebActivity.startAction(getActivity(),"组织关系",partyBuild_relation);
        }
    }


    @Override
    public void onNewsClick(int position, InformationEntity data) {
        mPresenter.getAddResVisitNumRequest(0,data.getId());
        if(data.getMediaType() == 0){
            MediaParamEntity info = new MediaParamEntity();
            info.setUrl(data.getUrl());
            info.setTitle(data.getName());
            info.setResId(data.getId());
            info.setResType(0);
            info.setCover(data.getIcon());
            info.setUserId(AppConfig.getInstance().getInt(AppConstant.USER_ID,-1));
            info.setCreateTime(data.getPublishTime());
            MediaPlayerActivity.startAction(getActivity(), info);
        }else{
            WebActivity.startAction(getActivity(),data.getId());
        }

    }
    @Override
    public void showLoading(String tag) {
        if(tag!=null){
            if(tag.equals("1003")){
                newTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
            if(tag.equals("1001")){
                shizhTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }

    }

    @Override
    public void stopLoading(String tag) {
        if(tag!=null){
            if(tag.equals("1003")){
                newTip.setLoadingTip(LoadingTip.LoadStatus.finish);
            }
            if(tag.equals("1001")){
                shizhTip.setLoadingTip(LoadingTip.LoadStatus.finish);
            }
        }

    }

    @Override
    public void showErrorTip(String msg,String tag) {
        if(tag!=null){
            if(tag.equals("1003")){
                newTip.setTips(msg);
            }
            if(tag.equals("1001")){
                shizhTip.setTips(msg);
            }
        }

    }
    //首页新闻数据返回
    @Override
    public void returnNewsListData(InformationResponse informationResponse, String tag) {
        List<InformationEntity> informations = informationResponse.getDataList();
        LogUtils.logd("returnNewsListData======================size=="+informations.size());
        LogUtils.logd("returnNewsListData======================"+informations);
        LogUtils.logd("returnNewsListData======================Tag==="+tag);
        if(informations != null && informations.size() > 0){
            if(tag!=null&&tag=="1003"&&tag.equals("1003")){
                newsList = informations;
                //请求查询浏览次数
                String ids = "";
                int size = newsList.size();
                for(int i = 0; i < size; i ++){
                    ids += newsList.get(i).getId()+",";
                }
                if(ids.endsWith(",")){
                    ids = ids.substring(0,ids.lastIndexOf(","));
                }
                mPresenter.getAllResVisitNumRequest("1003",0,ids);
               // newsRecyclerAdapter.setData(newsList);
            }else if(tag!=null&&tag=="1001"&&tag.equals("1001")){
                shizhNewsList = informations;
                //请求查询浏览次数
                String ids = "";
                int size = shizhNewsList.size();
                for(int i = 0; i < size; i ++){
                    ids += shizhNewsList.get(i).getId()+",";
                }
                if(ids.endsWith(",")){
                    ids = ids.substring(0,ids.lastIndexOf(","));
                }
                mPresenter.getAllResVisitNumRequest("1001",0,ids);
               // shizhRecyclerAdapter.setData(shizhNewsList);
            }
        }

    }

    @Override
    public void returnAddResVisitNum(BaseResponse response) {
        Log.i(TAG,"returnAddResVisitNum====================="+response.getResultCode());
    }

    @Override
    public void returnAllResVisitNum(BaseResponse<VisitNumEntity> baseResponse, String tag) {
        List<VisitNumEntity> visitNumEntities = baseResponse.getDataList();
        if(visitNumEntities != null && visitNumEntities.size() > 0){
            int size = visitNumEntities.size();
            if(tag != null && tag.equals("1001")){
                if(shizhNewsList == null){
                    return;
                }
                int size2 = shizhNewsList.size();
                for(int i = 0; i < size; i++){
                    VisitNumEntity visitNumEntity = visitNumEntities.get(i);
                    for(int j = 0 ; j < size2; j++){
                         InformationEntity informationEntity = shizhNewsList.get(j);
                         if(informationEntity.getId() == visitNumEntity.getId()){
                             Log.i(TAG,"returnAllResVisitNum==========resId:"+visitNumEntity.getId()+",watchNum:"+visitNumEntity.getNum());
                             informationEntity.setWatchNum(visitNumEntity.getNum());
                         }
                    }
                }
                shizhRecyclerAdapter.setData(shizhNewsList);
            }else if(tag != null && tag.equals("1003")){
                if(newsList == null){
                    return;
                }
                int size2 = newsList.size();
                for(int i = 0; i < size; i++){
                    VisitNumEntity visitNumEntity = visitNumEntities.get(i);
                    for(int j = 0 ; j < size2; j++){
                        InformationEntity informationEntity = newsList.get(j);
                        if(informationEntity.getId() == visitNumEntity.getId()){
                            Log.i(TAG,"returnAllResVisitNum==========resId:"+visitNumEntity.getId()+",watchNum:"+visitNumEntity.getNum());
                            informationEntity.setWatchNum(visitNumEntity.getNum());
                        }
                    }
                }
                newsRecyclerAdapter.setData(newsList);
            }
        }else{
            if(tag != null && tag.equals("1001")){
                shizhRecyclerAdapter.setData(shizhNewsList);
            }else if(tag != null && tag.equals("1003")){
                newsRecyclerAdapter.setData(newsList);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_news_textView:
                NewsActivity.startAction(getActivity(),"党建要闻");
                break;
            case R.id.shizh_more_news_textView:
                NewsActivity.startAction(getActivity(),"时政要闻");
                break;
            case R.id.home_notice_RelativeLayout:
//                WebActivity.startAction(getActivity(),"消息通知",mymessage);
                MyNoticeActivity.startAction(getActivity());
                break;
        }
    }

    //消息通知内容
    @Override
    public void returnListNoticeByTime(NoticeInfoEntity noticeInfoEntity, String tag) {
        if(noticeInfoEntity.getResultCode() == 200){
            List<NoticeInfoEntity.DataListBean> list = noticeInfoEntity.getDataList();
            if(list!=null&&list.size()>0){
                noticeMsgTv.setText(noticeInfoEntity.getDataList().get(0).getContent());
            }else{
                noticeMsgTv.setText("暂无消息");
            }
        }
    }

    //消息通知未读数量
    @Override
    public void returnUnReadNoticeNum(UserCollectionEntity userCollectionEntity, String tag) {
      if(userCollectionEntity.getResultCode() == 200){
          int data = userCollectionEntity.getData();
          if(data == 0){
              noReadSizeText.setVisibility(View.INVISIBLE);
          }else{
              noReadSizeText.setVisibility(View.VISIBLE);
              noReadSizeText.setText(userCollectionEntity.getData()+"");
          }

      }
    }
}
