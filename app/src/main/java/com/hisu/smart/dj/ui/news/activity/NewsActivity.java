package com.hisu.smart.dj.ui.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.hisu.smart.dj.entity.MediaParamEntity;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.adapter.NewsRecyclerAdapter;
import com.hisu.smart.dj.ui.main.contract.NewsListContract;
import com.hisu.smart.dj.ui.main.model.NewsListModel;
import com.hisu.smart.dj.ui.main.presenter.NewsListPresenter;
import com.hisu.smart.dj.ui.study.activity.StudyExperienceActivity;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.BannerWidget;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.commonutils.NetWorkUtils;
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
    @Bind(R.id.news_recycle_view)
    IRecyclerView newsIRecyclerView;
    @Bind(R.id.news_loadedTip)
    LoadingTip news_LoadTip;
    @Bind(R.id.follow_upLoad_imageView)
    ImageView follow_upload_img;
    private Banner newsBanner;


    private boolean isShowDiaTip = true;

    private static final String TAG = "NewsActivity";
    private List<Integer> newsBannerImages;
    private int[] images = {
            R.mipmap.home_banner_1,R.mipmap.home_banner_1,
            R.mipmap.home_banner_1,R.mipmap.home_banner_1};

    private static final String PARTY_NEWS = "1003"; //党建要闻
    private static final String SHIZ_NEWS = "1001"; //时政要闻
    private static final String SANH_Y_K = "3004"; //三会一课
    private static final String PARTY_AFFAIRS  = "4002"; //党务公开
    private static final String BRANCH_ACTIVITY = "4004"; //支部活动
    private static final String Time_Pioneer = "1005"; //时代先锋
    private static final String  Grassroots_Dynamics= "1007"; //基层动态
    private static int SIZE = 6;
    private int mStartPage;
    private String jump_tag;

    private String show_title;
    private int follow_id;
    private NewsRecyclerAdapter newsRecyclerAdapter;
    private int totalPages ;

    private List<InformationEntity> informations;
    private int resType = 0;
    private int resId = 0;
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
        mStartPage = 1;
        show_title = this.getIntent().getStringExtra(AppConstant.SHOW_TITLE);
        follow_id = this.getIntent().getIntExtra(AppConstant.FOLLOW_ID,-1);
        Log.d(TAG,"---show_title----"+show_title);
        newsBannerImages = new ArrayList<>();
        for(int i = 0; i < images.length; i++){
            newsBannerImages.add(images[i]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("NewsActivity","-----onResume()----");
        if(NetWorkUtils.isNetConnected(AppApplication.getAppContext())){
            if(AppConstant.isUpLoad||newsRecyclerAdapter.getData().size() == 0){
                AppConstant.isUpLoad = false;
                mStartPage = 1;
                newsRecyclerAdapter.getPageBean().setRefresh(true);
                showNewsType();
            }else{
                mPresenter.getResVisitNumRequest(resType,resId);
            }
        }else{
            Toast.makeText(this,"网络异常!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void initView() {
        title.setText(show_title);
        back_img.setOnClickListener(this);
        follow_upload_img.setOnClickListener(this);

        newsBanner = (Banner)LayoutInflater.from(this).inflate(R.layout.layout_banner_view, newsIRecyclerView.getHeaderContainer(), false);
        newsIRecyclerView.addHeaderView(newsBanner);
        newsBanner.setOnBannerListener(this);
        BannerWidget.setBanner(newsBanner,newsBannerImages);

        newsIRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsIRecyclerView.setAdapter(newsRecyclerAdapter);
        newsIRecyclerView.setOnLoadMoreListener(this);
        newsIRecyclerView.setOnRefreshListener(this);

    }

    public static void startAction(Activity activity, String title){
        Intent intent = new Intent(activity, NewsActivity.class);
        intent.putExtra(AppConstant.SHOW_TITLE,title);
        activity.startActivity(intent);
    }

    public static void startAction(Activity activity, String title,int follow_id){
        Intent intent = new Intent(activity, NewsActivity.class);
        intent.putExtra(AppConstant.SHOW_TITLE,title);
        intent.putExtra(AppConstant.FOLLOW_ID,follow_id);
        activity.startActivity(intent);
    }

    @Override
    public void returnNewsListData(InformationResponse informationResponse, String tag) {
        informations = informationResponse.getDataList();
        totalPages = informationResponse.getTotalPage();
        if(informations != null){
            mStartPage += 1;
            if (newsRecyclerAdapter.getPageBean().isRefresh()) {
                newsIRecyclerView.setRefreshing(false);
                int size = informations.size();
                String ids = "";
                for(int i = 0; i < size; i++){
                    ids += informations.get(i).getId()+",";
                }
                if(ids.endsWith(",")){
                    ids = ids.substring(0,ids.lastIndexOf(","));
                }
                if(tag != null && tag.equals(SANH_Y_K)){
                    mPresenter.getAllResVisitNumRequest("1",2,ids);
                }else {
                    mPresenter.getAllResVisitNumRequest("1",0,ids);
                }
            } else {
                if (informations.size() > 0) {
                    newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    int size = informations.size();
                    String ids = "";
                    for(int i = 0; i < size; i++){
                        ids += informations.get(i).getId()+",";
                    }
                    if(ids.endsWith(",")){
                        ids = ids.substring(0,ids.lastIndexOf(","));
                    }
                    if(tag != null && tag.equals(SANH_Y_K)){
                        mPresenter.getAllResVisitNumRequest("2",2,ids);
                    }else {
                        mPresenter.getAllResVisitNumRequest("2",0,ids);
                    }
                } else {
                    newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void returnResVisitNum(VisitNumResponse visitNumResponse) {
        int num = visitNumResponse.getData();
        List<InformationEntity> informationList = newsRecyclerAdapter.getData();
        if(informationList != null){
            for(InformationEntity entity : informationList){
                if(entity.getId() == resId){
                    entity.setWatchNum(num);
                }
            }
        }
        newsRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnAddResVisitNum(BaseResponse response) {
        Log.i(TAG,"returnAddResVisitNum===================="+response.getResultCode());
    }

    @Override
    public void returnAllResVisitNum(BaseResponse<VisitNumEntity> baseResponse, String tag) {
        List<VisitNumEntity> visitNumEntities = baseResponse.getDataList();
        if(visitNumEntities != null && visitNumEntities.size() > 0){
            int size = visitNumEntities.size();
            if(tag != null && tag.equals("1")){
                if(informations == null){
                    return;
                }
                int size2 = informations.size();
                for(int i = 0; i < size; i++){
                    VisitNumEntity visitNumEntity = visitNumEntities.get(i);
                    for(int j = 0 ; j < size2; j++){
                        InformationEntity informationEntity = informations.get(j);
                        if(informationEntity.getId() == visitNumEntity.getId()){
                            Log.i(TAG,"returnAllResVisitNum==========resId:"+visitNumEntity.getId()+",watchNum:"+visitNumEntity.getNum());
                            informationEntity.setWatchNum(visitNumEntity.getNum());
                        }
                    }
                }
                newsRecyclerAdapter.setData(informations);
            }else if(tag != null && tag.equals("2")){
                if(informations == null){
                    return;
                }
                int size2 = informations.size();
                for(int i = 0; i < size; i++){
                    VisitNumEntity visitNumEntity = visitNumEntities.get(i);
                    for(int j = 0 ; j < size2; j++){
                        InformationEntity informationEntity = informations.get(j);
                        if(informationEntity.getId() == visitNumEntity.getId()){
                            Log.i(TAG,"returnAllResVisitNum==========resId:"+visitNumEntity.getId()+",watchNum:"+visitNumEntity.getNum());
                            informationEntity.setWatchNum(visitNumEntity.getNum());
                        }
                    }
                }
                newsRecyclerAdapter.addAll(informations);
            }
        }else{
            if(tag != null && tag.equals("1")){
                newsRecyclerAdapter.setData(informations);
            }else if(tag != null && tag.equals("2")){
                newsRecyclerAdapter.addAll(informations);
            }
        }
    }

    @Override
    public void showLoading(String tag) {
        if(isShowDiaTip){
            news_LoadTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }

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
           case R.id.follow_upLoad_imageView:
               String cateId = String.valueOf(follow_id);
               cateId = cateId.substring(cateId.length()-1,cateId.length());
               StudyExperienceActivity.startAction(this,show_title,Integer.valueOf(cateId));
               break;
       }
    }
    //Banner图点击事件
    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void onNewsClick(int position,InformationEntity data) {
        Log.d(TAG,"----item----position----"+position);
//       Toast.makeText(this,"item-id=="+news_id,
//               Toast.LENGTH_LONG).show();
        resId = data.getId();
        AppConstant.isUpLoad = false;
        if(follow_id!=-1){
            jump_tag = "践行活动";
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
                MediaPlayerActivity.startAction(this, info);
            }else{
                WebActivity.startAction(this,data.getId(),jump_tag);
            }
        }else if(show_title.equals("三会一课")){
            jump_tag = "专题学习";
            mPresenter.getAddResVisitNumRequest(2,data.getId());
            if(data.getMediaType() == 0){
                MediaParamEntity info = new MediaParamEntity();
                info.setUrl(data.getUrl());
                info.setTitle(data.getName());
                info.setResId(data.getId());
                info.setResType(2);
                info.setCover(data.getIcon());
                info.setUserId(AppConfig.getInstance().getInt(AppConstant.USER_ID,-1));
                info.setCreateTime(data.getPublishTime());
                MediaPlayerActivity.startAction(this, info);
            }else {
                WebActivity.startAction(this, data.getId(), jump_tag);
            }
        }else{
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
                MediaPlayerActivity.startAction(this, info);
            }else {
                WebActivity.startAction(this, data.getId());
            }
        }

    }
    //加载
    @Override
    public void onLoadMore(View loadMoreView) {
        isShowDiaTip = false;
        newsRecyclerAdapter.getPageBean().setRefresh(false);
        //发起请求
        if(totalPages >= mStartPage){
            showNewsType();
            newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        }else{
            newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        isShowDiaTip = false;
        newsRecyclerAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        newsIRecyclerView.setRefreshing(true);

        showNewsType();//
        newsIRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
    }
    //判断显示哪类新闻
    private void showNewsType(){
        if(show_title.equals("三会一课")){
            resType = 2;
            mPresenter.getTopicListContentRequest(SANH_Y_K,null,mStartPage,SIZE);
        }else if(show_title.equals("支部活动")){
            mPresenter.getNewsListDataRequest(BRANCH_ACTIVITY,null,mStartPage,SIZE);
        }else if(show_title.equals("党务公开")){
            mPresenter.getNewsListDataRequest(PARTY_AFFAIRS,null,mStartPage,SIZE);
        }else if(show_title.equals("党建要闻")){
            mPresenter.getNewsListDataRequest(PARTY_NEWS,null,mStartPage,SIZE);
        }else if(show_title.equals("时政要闻")){
            mPresenter.getNewsListDataRequest(SHIZ_NEWS,null,mStartPage,SIZE);
        }else if(follow_id!=-1){
            follow_upload_img.setVisibility(View.VISIBLE);
            Log.d("NewsActivity","践行-----follow_id==="+follow_id);
            Log.d("NewsActivity","践行-----mStartPage==="+mStartPage);
            mPresenter.getListActionContentRequest(String.valueOf(follow_id),null,mStartPage,SIZE);
        }else if(show_title.equals("时代先锋")){
            mPresenter.getNewsListDataRequest(Time_Pioneer,null,mStartPage,SIZE);
        }else if(show_title.equals("基层动态")){
            mPresenter.getNewsListDataRequest(Grassroots_Dynamics,null,mStartPage,SIZE);
        }

    }
}
