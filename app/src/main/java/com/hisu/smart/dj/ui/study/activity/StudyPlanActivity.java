package com.hisu.smart.dj.ui.study.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.ui.main.activity.WelcomeActivity;
import com.hisu.smart.dj.ui.study.contract.StudyPlanContract;
import com.hisu.smart.dj.ui.study.model.StudyPlanModel;
import com.hisu.smart.dj.ui.study.presenter.StudyPlanPresenter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.NetWorkUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import butterknife.Bind;

/**
 * 学习计划页面
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class StudyPlanActivity extends BaseActivity<StudyPlanPresenter,StudyPlanModel>
        implements View.OnClickListener,StudyPlanContract.View{
    @Bind(R.id.title_TextView)
    TextView title_text;//标题
    @Bind(R.id.back_imageView)
    ImageView back_img;//返回按钮
    @Bind(R.id.mouth_plan_textView)
    TextView mouth_plan;//月度
    @Bind(R.id.quarter_plan_textView)
    TextView quarter_plan;//季度
    @Bind(R.id.year_plan_textView)
    TextView year_plan; //年度
    @Bind(R.id.show_planname_textView)
    TextView show_plan_title; //选中要查看的计划
    @Bind(R.id.show_expert_study_textView)
    TextView show_expert_study;//专题学习总展示
    @Bind(R.id.show_study_state_textView)
    TextView show_expert_state; //专题学习的学习进度
    @Bind(R.id.expert_study_progressBar)
    ProgressBar expert_progress; //专题学习进度条
    @Bind(R.id.show_routine_study_textView)
    TextView show_routine_study; //常规学习总展示
    @Bind(R.id.show_routine_study_state_textView)
    TextView show_routine_state; //常规学习进度
    @Bind(R.id.routine_study_progressBar)
    ProgressBar routine_progress; //常规学习进度条

    private static final Integer MOUTH_TYPE = 2; //月度计划
    private static final Integer QUARTER_TYPE = 1; //季度计划
    private static final Integer YEAR_TYPE = 0; //年度度计划

    private Integer user_id;       //用户ID
    private Integer partyMemberId; //党员ID
    private Integer partyBranchId; //支部ID

    private double expert_totalHours_mouth = -1; //专题学习已学习时长 月度
    private int expert_planTotalHours_mouth = -1; //专题学习要学习时总时长

    private double routine_totalHours_mouth = -1; //常规学习已学习时长
    private int routine_planTotalHours_mouth = -1; //常规学习要学习时总时长

    private double expert_totalHours_quarter = -1; //专题学习已学习时长 季度
    private int expert_planTotalHours_quarter = -1; //专题学习要学习时总时长

    private double routine_totalHours_quarter = -1; //常规学习已学习时长
    private int routine_planTotalHours_quarter = -1; //常规学习要学习时总时长

    private double expert_totalHours_year = -1; //专题学习已学习时长 年度
    private int expert_planTotalHours_year = -1; //专题学习要学习时总时长

    private double routine_totalHours_year = -1; //常规学习已学习时长
    private int routine_planTotalHours_year = -1; //常规学习要学习时总时长

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, StudyPlanActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void initView() {
        mouth_plan.setOnClickListener(this);
        quarter_plan.setOnClickListener(this);
        year_plan.setOnClickListener(this);
        back_img.setOnClickListener(this);

        if(NetWorkUtils.isNetConnected(AppApplication.getAppContext())){
            mouth_plan.setTextColor(Color.BLACK);
            if(AppConstant.IS_STUDY_BRANCH){
                Log.d("StudyPlanActivity","---查询支部学习计划---");
                //默认查看月度（支部）
                getBranchPlan(MOUTH_TYPE);
            }else{
                //默认查看月度(个人)
                Log.d("StudyPlanActivity","---查询个人学习计划---");
                getMemberPlan(MOUTH_TYPE);
            }


        }else{
            Toast.makeText(this,"网络异常！",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_plan;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);

        user_id = AppConfig.getInstance().getInt(AppConstant.USER_ID,-1);
        partyMemberId = AppConfig.getInstance().getInt(AppConstant.MEMBER_ID,-1);
        partyBranchId = AppConfig.getInstance().getInt(AppConstant.MEMBER_PARTYBRANCH_ID,-1);

        if(partyMemberId == -1){
            partyMemberId = null;
        }
        if(partyBranchId == -1){
            partyBranchId = null;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mouth_plan_textView:
                mouth_plan.setTextColor(Color.BLACK);
                quarter_plan.setTextColor(R.color.background_color_gory);
                year_plan.setTextColor(R.color.background_color_gory);
                if(expert_planTotalHours_mouth!=-1&&expert_totalHours_mouth!=-1
                        &&routine_totalHours_mouth!=-1&&routine_planTotalHours_mouth!=-1){
                    setExpertData(expert_totalHours_mouth,expert_planTotalHours_mouth);
                    setRoutineData(routine_totalHours_mouth,routine_planTotalHours_mouth);
                }else{
                    getChangedData(MOUTH_TYPE);
                }
                break;
            case R.id.quarter_plan_textView:
                quarter_plan.setTextColor(Color.BLACK);
                mouth_plan.setTextColor(R.color.background_color_gory);
                year_plan.setTextColor(R.color.background_color_gory);

                if(expert_planTotalHours_quarter!=-1&&expert_totalHours_quarter!=-1
                        &&routine_totalHours_quarter!=-1&&routine_planTotalHours_quarter!=-1){

                    setExpertData(expert_totalHours_quarter,expert_planTotalHours_quarter);
                    setRoutineData(routine_totalHours_quarter,routine_planTotalHours_quarter);

                }else{
                    getChangedData(QUARTER_TYPE);
                }
                break;
            case R.id.year_plan_textView:
                year_plan.setTextColor(Color.BLACK);
                mouth_plan.setTextColor(R.color.background_color_gory);
                quarter_plan.setTextColor(R.color.background_color_gory);
                if(expert_planTotalHours_year!=-1&&expert_totalHours_year!=-1
                        &&routine_totalHours_year!=-1&&routine_planTotalHours_year!=-1){

                    setExpertData(expert_totalHours_year,expert_planTotalHours_year);
                    setRoutineData(routine_totalHours_year,routine_planTotalHours_year);

                }else{
                    getChangedData(YEAR_TYPE);
                }
                break;
            case R.id.back_imageView:
                StudyPlanActivity.this.finish();
                break;
            default:
        }
    }
    //个人
    @Override
    public void returnMemberPlanData(StudyPlanRespone studyPlanRespone, Integer timeType) {
        setReturnData(studyPlanRespone,timeType);
    }
    //支部
    @Override
    public void returnBranchPlanData(StudyPlanRespone studyPlanRespone, Integer timeType) {
        setReturnData(studyPlanRespone,timeType);
    }

    @Override
    public void showLoading(String tag) {

    }

    @Override
    public void stopLoading(String tag) {

    }

    @Override
    public void showErrorTip(String msg, String tag) {

    }
    //个人学习计划
    private void getMemberPlan(Integer timeType){
      mPresenter.getMemberPlanDataRequest(user_id,partyMemberId,timeType);
    }
    //支部学习计划
    private void getBranchPlan(Integer timeType){
        mPresenter.getBranchPlanDataRequest(user_id,partyBranchId,timeType);
    }

    //设置专题学习需要展示的数据
    private void setExpertData(double expert_totalHours,int expert_planTotalHours){
        show_expert_study.setText("学时计划：已学习"+expert_totalHours
                +"课时/计划学习"+expert_planTotalHours+"课时");
        expert_progress.setMax(expert_planTotalHours);
        expert_progress.setProgress((int)expert_totalHours,true);

        double learing_state_expert = expert_totalHours/expert_planTotalHours;

        String learning_state_str = null;
        BigDecimal bigDecimal = new BigDecimal(learing_state_expert);
        learing_state_expert = bigDecimal.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();

        if(learing_state_expert<=1){
            learning_state_str = learing_state_expert * 100 + "%";
        }else{
            learning_state_str = "100%";
        }
        show_expert_state.setText("已学习"+learning_state_str);
    }

    //设置专题学习需要展示的数据
    private void setRoutineData(double routine_totalHours,int toutine_planTotalHours){
        show_routine_study.setText("学时计划：已学习"+routine_totalHours
                +"课时/计划学习"+toutine_planTotalHours+"课时");
        routine_progress.setMax(toutine_planTotalHours);
        routine_progress.setProgress((int)routine_totalHours,true);

        double learing_state_routine = routine_totalHours/toutine_planTotalHours;
        String learning_state_str = null;

        BigDecimal bigDecimal = new BigDecimal(learing_state_routine);
        learing_state_routine = bigDecimal.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
        if(learing_state_routine<=1){
            learning_state_str = learing_state_routine*100 + "%";
        }else{
            learning_state_str = "100%";
        }
        show_routine_state.setText("已学习"+learning_state_str);
    }

    //处理服务器返回的数据
    private void setReturnData(StudyPlanRespone studyPlanRespone,Integer timeType){
        String code = String.valueOf(studyPlanRespone.getResultCode());
        //返回200
        if(AppConstant.REQUEST_SUCCESS.equals(code)){
            //常规学习Bean
            StudyPlanRespone.DataBean.CommPlanBean commPlanBean = studyPlanRespone.getData()
                    .getCommPlan();
            //专题学习Bean
            StudyPlanRespone.DataBean.TopicPlanBean topicPlanBean = studyPlanRespone.getData()
                    .getTopicPlan();
            if(commPlanBean!=null && topicPlanBean!=null){
                if(timeType == MOUTH_TYPE){ //月度
                    //专题学习
                    expert_totalHours_mouth = topicPlanBean.getTotalHours(); //已经学习进度
                    expert_planTotalHours_mouth = topicPlanBean.getPlanTotalHours(); //共学习的时长
                    //常规学习
                    routine_totalHours_mouth = commPlanBean.getTotalHours();
                    routine_planTotalHours_mouth = commPlanBean.getPlanTotalHours();
                    //设置专题学习数据
                    setExpertData(expert_totalHours_mouth,expert_planTotalHours_mouth);
                    //设置常规学习
                    setRoutineData(routine_totalHours_mouth,routine_planTotalHours_mouth);

                }else if(timeType == QUARTER_TYPE){//季度
                    //专题学习
                    expert_totalHours_quarter = topicPlanBean.getTotalHours(); //已经学习进度
                    expert_planTotalHours_quarter = topicPlanBean.getPlanTotalHours(); //共学习的时长
                    //常规学习
                    routine_totalHours_quarter = commPlanBean.getTotalHours();
                    routine_planTotalHours_quarter = commPlanBean.getPlanTotalHours();

                    //设置专题学习数据
                    setExpertData(expert_totalHours_quarter,expert_planTotalHours_quarter);
                    //设置常规学习
                    setRoutineData(routine_totalHours_quarter,routine_planTotalHours_quarter);

                }else if(timeType == YEAR_TYPE){//年度
                    expert_totalHours_year = topicPlanBean.getTotalHours(); //已经学习进度
                    expert_planTotalHours_year = topicPlanBean.getPlanTotalHours(); //共学习的时长
                    //常规学习
                    routine_totalHours_year = commPlanBean.getTotalHours();
                    routine_planTotalHours_year = commPlanBean.getPlanTotalHours();

                    //设置专题学习数据
                    setExpertData(expert_totalHours_year,expert_planTotalHours_year);
                    //设置常规学习
                    setRoutineData(routine_totalHours_year,routine_planTotalHours_year);

                }
            }else{
                Toast.makeText(StudyPlanActivity.this,
                        "暂无数据！",Toast.LENGTH_LONG).show();
            }
        }
    }
    //点击月度，季度，年度，无数据时调用
    private void getChangedData(Integer timeType){
        if(AppConstant.IS_STUDY_BRANCH){
            getBranchPlan(timeType);
        }else{
            getMemberPlan(timeType);
        }
    }
}
