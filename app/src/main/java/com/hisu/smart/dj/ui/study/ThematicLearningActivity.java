package com.hisu.smart.dj.ui.study;

import android.app.Activity;
import android.content.Intent;

import com.hisu.smart.dj.R;
import com.jaydenxiao.common.base.BaseActivity;

/**
 * @author lichee
 */
public class ThematicLearningActivity extends BaseActivity {

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, ThematicLearningActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_thematic_learning;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }
}
