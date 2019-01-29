package com.hisu.smart.dj.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;

import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.widget.ProfileEdit;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;


/**
 * 我的
 *
 * @author lichee
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    private ImageView img_sao;
    private ImageView img_head;
    private TextView tv_name;
    private TextView tv_update;
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

    private void findAllView() {
        img_sao = rootView.findViewById(R.id.img_sao);
        img_head = rootView.findViewById(R.id.img_my_head);
        tv_name = rootView.findViewById(R.id.tv_my_name);
        tv_update = rootView.findViewById(R.id.tv_my_update);
        msg = rootView.findViewById(R.id.pe_msg);
        score = rootView.findViewById(R.id.pe_score);
        approval = rootView.findViewById(R.id.pe_approval);
        collect = rootView.findViewById(R.id.pe_collect);
        setting = rootView.findViewById(R.id.pe_setting);

        tv_name.setText(AppConfig.getInstance().getString(AppConstant.NICK_NAME,""));
        ImageLoaderUtils.display(getActivity(),img_head,AppConfig.getInstance().getString(AppConstant.USER_PHOTO,""));

        img_sao.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        msg.setOnClickListener(this);
        score.setOnClickListener(this);
        approval.setOnClickListener(this);
        collect.setOnClickListener(this);
        setting.setOnClickListener(this);
    }


    private void setIconKey() {
        msg.set(R.mipmap.pre_message, "消息通知");
        score.set(R.mipmap.pre_integral, "我的积分");
        approval.set(R.mipmap.pre_confirm, "我的审批");
        collect.set(R.mipmap.pre_likes, "我的收藏");
        setting.set(R.mipmap.pre_set, "设置");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //扫一扫
            case R.id.img_sao:
                break;
            //修改
            case R.id.tv_my_update:
                break;
            //消息通知
            case R.id.pe_msg:
                break;
            //我的积分
            case R.id.pe_score:
                break;
            //我的审批
            case R.id.pe_approval:
                break;
            //我的收藏
            case R.id.pe_collect:
                break;
            //设置
            case R.id.pe_setting:
                break;
            default:
                break;
        }

    }
}
