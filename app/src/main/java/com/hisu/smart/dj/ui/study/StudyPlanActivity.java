package com.hisu.smart.dj.ui.study;

import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.Bind;

/**
 * 学习计划页面
 */
public class StudyPlanActivity extends BaseActivity implements View.OnClickListener{
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_plan;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mouth_plan_textView:
                break;
            case R.id.quarter_plan_textView:
                break;
            case R.id.year_plan_textView:
                break;
            case R.id.back_imageView:
                StudyPlanActivity.this.finish();
                break;
            default:
        }
    }
}
