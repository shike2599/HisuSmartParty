package com.hisu.smart.dj.ui.study.activity;


import android.app.Activity;
import android.content.Intent;

import com.hisu.smart.dj.R;
import com.jaydenxiao.common.base.BaseActivity;

/**
 * 学习心得页面（可能会复用）
 */
public class StudyExperienceActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_study_experience;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, StudyExperienceActivity.class);
        activity.startActivity(intent);
    }
}
