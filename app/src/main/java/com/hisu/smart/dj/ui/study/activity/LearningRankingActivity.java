package com.hisu.smart.dj.ui.study.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.Bind;

public class LearningRankingActivity extends BaseActivity implements
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
    TextView back_img;
    @Override
    public int getLayoutId() {
        return R.layout.activity_learning_ranking;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
       show_branch_data.setOnClickListener(this);
       show_user_data.setOnClickListener(this);
       back_img.setOnClickListener(this);
    }

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, LearningRankingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.branch_rank_textView:
                break;
            case R.id.user_rank_textView:
                break;
            case R.id.rank_back_imageView:
                LearningRankingActivity.this.finish();
                break;
        }
    }
}
