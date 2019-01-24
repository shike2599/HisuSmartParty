package com.hisu.smart.dj.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;

import com.hisu.smart.dj.R;

import com.hisu.smart.dj.ui.widget.ProfileEdit;
import com.jaydenxiao.common.base.BaseFragment;


/**
 * 我的
 * @author lichee
 */
public class MyFragment extends BaseFragment {


    private ProfileEdit msg;
    private ProfileEdit score;
    private ProfileEdit approval;
    private ProfileEdit collect;
    private ProfileEdit setting;

    public MyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        findAllView();
        setIconKey();
    }

    private void findAllView(){
        msg = rootView.findViewById(R.id.pe_msg);
        score = rootView.findViewById(R.id.pe_score);
        approval = rootView.findViewById(R.id.pe_approval);
        collect = rootView.findViewById(R.id.pe_collect);
        setting = rootView.findViewById(R.id.pe_setting);
    }


    private void setIconKey() {
        msg.set(R.mipmap.pre_message,"消息通知");
        score.set(R.mipmap.pre_integral,"我的积分");
        approval.set(R.mipmap.pre_confirm,"我的审批");
        collect.set(R.mipmap.pre_likes,"我的收藏");
        setting.set(R.mipmap.pre_set,"设置");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
