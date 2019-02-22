package com.hisu.smart.dj.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;

import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.main.activity.MainActivity;
import com.hisu.smart.dj.ui.my.activity.MyNoticeActivity;
import com.hisu.smart.dj.ui.my.activity.SettingActivity;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.ProfileEdit;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.znq.zbarcode.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;


/**
 * 我的
 *
 * @author lichee
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    private static final String MYCENTE = "mycenter/";
    //消息通知
    private static String mymessage = AppConstant.BASE_URL_LOAD+MYCENTE+"mymessage.html";
    //我的积分
    private static String myIntegral = AppConstant.BASE_URL_LOAD+MYCENTE+"myIntegral.html";
    //我的审批
    private static String myApproval = AppConstant.BASE_URL_LOAD+MYCENTE+"myApproval.html";
    //我的收藏
    private static String myCollection = AppConstant.BASE_URL_LOAD+MYCENTE+"myCollection.html";
//    private ImageView img_sao;

    private ImageView img_head;
    private TextView tv_name;
    private TextView tv_update;
    private ProfileEdit msg;
    private ProfileEdit score;
    private ProfileEdit approval;
    private ProfileEdit collect;
    private ProfileEdit setting;
    private ProfileEdit qr_code;
    private Context context;
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
        context = getActivity();
    }

    @Override
    protected void initView() {
        findAllView();
        setIconKey();
    }

    private void findAllView() {
//        img_sao = rootView.findViewById(R.id.img_sao);
        qr_code = rootView.findViewById(R.id.pe_qrcode);
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

//        img_sao.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        msg.setOnClickListener(this);
        score.setOnClickListener(this);
        approval.setOnClickListener(this);
        collect.setOnClickListener(this);
        qr_code.setOnClickListener(this);
        setting.setOnClickListener(this);
    }


    private void setIconKey() {
        msg.set(R.mipmap.pre_message, "消息通知");
        score.set(R.mipmap.pre_integral, "我的积分");
        approval.set(R.mipmap.pre_confirm, "我的审批");
        collect.set(R.mipmap.pre_likes, "我的收藏");
        qr_code.set(R.mipmap.pre_qrcode_icon,"扫一扫登录");
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
            case R.id.pe_qrcode:
                Intent intent = new Intent();
                intent.setClass(getActivity(), CaptureActivity.class);
                getActivity().startActivityForResult(intent, AppConstant.QR_CODE);
                break;
            //修改
            case R.id.tv_my_update:
                break;
            //消息通知
            case R.id.pe_msg:
//                WebActivity.startAction(getActivity(),"消息通知",mymessage);
                MyNoticeActivity.startAction(getActivity());
                break;
            //我的积分
            case R.id.pe_score:
                WebActivity.startAction(getActivity(),"我的积分",myIntegral);
                break;
            //我的审批
            case R.id.pe_approval:
                WebActivity.startAction(getActivity(),"我的审批",myApproval);
                break;
            //我的收藏
            case R.id.pe_collect:
                WebActivity.startAction(getActivity(),"我的收藏",myCollection);
                break;
            //设置
            case R.id.pe_setting:
                SettingActivity.startAction(getActivity());
                break;
            default:
                break;
        }

    }

}
