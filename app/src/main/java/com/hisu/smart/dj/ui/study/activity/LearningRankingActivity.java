package com.hisu.smart.dj.ui.study.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.irecyclerview.universaladapter.recyclerview.DividerItemDecoration;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.entity.RankEntity;
import com.hisu.smart.dj.ui.adapter.StudyRankAdapter;
import com.hisu.smart.dj.ui.study.contract.StudyRankContract;
import com.hisu.smart.dj.ui.study.model.StudyRankModel;
import com.hisu.smart.dj.ui.study.presenter.StudyRankPresenter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class LearningRankingActivity extends BaseActivity
        <StudyRankPresenter,StudyRankModel> implements StudyRankContract.View,
        View.OnClickListener{

    @Bind(R.id.branch_rank_textView)
    TextView show_branch_data;
    @Bind(R.id.user_rank_textView)
    TextView show_user_data;
    @Bind(R.id.all_learing_recyclerView)
    RecyclerView all_learing_recycler;
    @Bind(R.id.expert_learing_recyclerView)
    RecyclerView expert_learing_recycler;
    @Bind(R.id.routine__learing_recyclerView)
    RecyclerView routine_learing_recycler;
    @Bind(R.id.show_all_learing_textView)
    TextView show_all_learing;
    @Bind(R.id.show_expert_learing_textView)
    TextView show_expert_learing;
    @Bind(R.id.show_routine__learing_textView)
    TextView show_routine_learing;
    @Bind(R.id.rank_back_imageView)
    ImageView back_img;
    @Bind(R.id.loadedTip_all)
    LoadingTip total_loadedTip;//总学时加载框
    @Bind(R.id.loadedTip_expert)
    LoadingTip topic_loadedTip;//专题加载框
    @Bind(R.id.loadedTip_routine)
    LoadingTip comm_loadedTip;//总学时加载框


    private final static int TOTAL_TYPE = 0; //总学时
    private final static int TOPIC_TYPE = 1; //专题
    private final static int COMM_TYPE = 2; //常规

    private final static int limit_Num = 6; //去数据的个数


    private StudyRankAdapter totalHoursAdapter; //总学时排名
    private StudyRankAdapter topicHoursAdapter; //专题学习排名
    private StudyRankAdapter commHoursAdapter; //常规学习排名

    private Integer user_id;       //用户ID
    private Integer partyMemberId; //党员ID
    private Integer partyBranchId; //支部ID
    //个人信息
    private List<RankEntity> user_dataList_total; //总学时数据
    private List<RankEntity> user_dataList_topic; //专题数据
    private List<RankEntity> user_dataList_comm; //常规数据
    //支部信息
    private List<RankEntity> branch_dataList_total; //总学时数据
    private List<RankEntity> branch_dataList_topic; //专题数据
    private List<RankEntity> branch_dataList_comm; //常规数据

    @Override
    public int getLayoutId() {
        return R.layout.activity_learning_ranking;
    }

    @Override
    public void initPresenter() {
        user_id = 5;
        partyMemberId = 1;
        partyBranchId = 1;

        user_dataList_total = new ArrayList<>();
        user_dataList_topic = new ArrayList<>();
        user_dataList_comm = new ArrayList<>();

        branch_dataList_total = new ArrayList<>();
        branch_dataList_topic = new ArrayList<>();
        branch_dataList_comm = new ArrayList<>();

        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
       show_branch_data.setOnClickListener(this);
       show_user_data.setOnClickListener(this);
       back_img.setOnClickListener(this);
       //总学时布局
       all_learing_recycler.setLayoutManager(new LinearLayoutManager(this));
       all_learing_recycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
       totalHoursAdapter = new StudyRankAdapter(TOTAL_TYPE);
       all_learing_recycler.setAdapter(totalHoursAdapter);
       //专题学习布局
       expert_learing_recycler.setLayoutManager(new LinearLayoutManager(this));
       expert_learing_recycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
       topicHoursAdapter = new StudyRankAdapter(TOPIC_TYPE);
       expert_learing_recycler.setAdapter(topicHoursAdapter);
       //常规学习布局
       routine_learing_recycler.setLayoutManager(new LinearLayoutManager(this));
       routine_learing_recycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
       commHoursAdapter = new StudyRankAdapter(COMM_TYPE);
       routine_learing_recycler.setAdapter(commHoursAdapter);
       if(AppApplication.isNet){
           //默认是选择支部
           show_branch_data.setTextColor(Color.BLACK);
           getBranchData();
       }else{
           Toast.makeText(this,"网络异常",Toast.LENGTH_LONG).show();
       }

    }

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, LearningRankingActivity.class);
        activity.startActivity(intent);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.branch_rank_textView:
                show_branch_data.setTextColor(Color.BLACK);
                show_user_data.setTextColor(R.color.background_color_gory);
                if(branch_dataList_total.size()>0&&
                        branch_dataList_topic.size()>0&&
                        branch_dataList_comm.size()>0){
                    totalHoursAdapter.setData(branch_dataList_total);
                    topicHoursAdapter.setData(branch_dataList_total);
                    commHoursAdapter.setData(branch_dataList_total);
                }else{
                    getBranchData();
                }
                break;
            case R.id.user_rank_textView:
                show_user_data.setTextColor(Color.BLACK);
                show_branch_data.setTextColor(R.color.background_color_gory);
                if(user_dataList_total.size()>0&&
                        user_dataList_topic.size()>0&&
                        user_dataList_comm.size()>0){
                    totalHoursAdapter.setData(user_dataList_total);
                    topicHoursAdapter.setData(user_dataList_total);
                    commHoursAdapter.setData(user_dataList_total);
                }else{
                    getUserData();
                }
                break;
            case R.id.rank_back_imageView:
                LearningRankingActivity.this.finish();
                break;
        }
    }
    //个人排行
    @Override
    public void returnMemberRankListData(List<RankEntity> rankEntities,Integer sortType) {
       if(rankEntities != null && rankEntities.size() > 0){
           if(sortType==TOTAL_TYPE){
               user_dataList_total=rankEntities;
               totalHoursAdapter.setData(user_dataList_total);
           }else if(sortType==TOPIC_TYPE){
               user_dataList_topic=rankEntities;
               topicHoursAdapter.setData(user_dataList_topic);
           }else if(sortType==COMM_TYPE){
               user_dataList_comm=rankEntities;
               commHoursAdapter.setData(user_dataList_comm);
           }
       }
    }
    //支部排行
    @Override
    public void returnBranchRankListData(List<RankEntity> rankEntities,Integer sortType) {
//        Log.d("LearningRank","rankEntities---size==="+rankEntities.size());
        if(rankEntities != null && rankEntities.size() > 0){
            if(sortType==TOTAL_TYPE){
                branch_dataList_total=rankEntities;
                totalHoursAdapter.setData(branch_dataList_total);
            }else if(sortType==TOPIC_TYPE){
                branch_dataList_topic=rankEntities;
                topicHoursAdapter.setData(branch_dataList_topic);
            }else if(sortType==COMM_TYPE){
                branch_dataList_comm=rankEntities;
                commHoursAdapter.setData(branch_dataList_comm);
            }
        }
    }

    @Override
    public void showLoading(String tag) {
       if(tag.equals(String.valueOf(TOTAL_TYPE))
               &&tag==String.valueOf(TOTAL_TYPE)){
           Log.d("LearningRank","--showLoading--"+tag);
           total_loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
       }
       if(tag.equals(String.valueOf(TOPIC_TYPE))
               &&tag==String.valueOf(TOPIC_TYPE)){
           topic_loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
       }
       if(tag.equals(String.valueOf(COMM_TYPE))&&tag==String.valueOf(COMM_TYPE)){
           comm_loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
       }
    }

    @Override
    public void stopLoading(String tag) {
        if(tag.equals(String.valueOf(TOTAL_TYPE))&&tag==String.valueOf(TOTAL_TYPE)){
            total_loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
        if(tag.equals(String.valueOf(TOPIC_TYPE))&&tag==String.valueOf(TOPIC_TYPE)){
            topic_loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
        if(tag.equals(String.valueOf(COMM_TYPE))&&tag==String.valueOf(COMM_TYPE)){
            comm_loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        if(tag.equals(String.valueOf(TOTAL_TYPE))&&tag==String.valueOf(TOTAL_TYPE)){
            total_loadedTip.setTips(msg);
        }
        if(tag.equals(String.valueOf(TOPIC_TYPE))&&tag==String.valueOf(TOPIC_TYPE)){
            topic_loadedTip.setTips(msg);
        }
        if(tag.equals(String.valueOf(COMM_TYPE))&&tag==String.valueOf(COMM_TYPE)){
            comm_loadedTip.setTips(msg);
        }
    }

    //获取支部数据
    public void getBranchData() {
        //按照学时排列
        mPresenter.getBranchRankListDataRequest(user_id,partyBranchId,TOTAL_TYPE,limit_Num);
        //按照专题排列
        mPresenter.getBranchRankListDataRequest(user_id,partyBranchId,TOPIC_TYPE,limit_Num);
        //按照常规排列
        mPresenter.getBranchRankListDataRequest(user_id,partyBranchId,COMM_TYPE,limit_Num);
    }

    //获取党员个人数据
    public void getUserData() {
        //按照学时排列
        mPresenter.getMemberRankListDataRequest(user_id,partyMemberId,TOTAL_TYPE,limit_Num);
        //按照专题排列
        mPresenter.getMemberRankListDataRequest(user_id,partyMemberId,TOPIC_TYPE,limit_Num);
        //按照常规排列
        mPresenter.getMemberRankListDataRequest(user_id,partyMemberId,COMM_TYPE,limit_Num);
    }
}
