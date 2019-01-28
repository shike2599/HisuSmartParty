package com.hisu.smart.dj.ui.my.activity;

import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;

import com.hisu.smart.dj.utils.CountDownTimerUtils;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.Bind;

/**
 * @author lichee
 */
public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView back_img;//返回按钮
    @Bind(R.id.btn_find_password)
    Button mFindPassword;
    @Bind(R.id.tv_send_code)
    TextView mSendCode;
    CountDownTimerUtils countDownTimer;

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, ForgotPasswordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        mTitle.setText("找回密码");
        back_img.setOnClickListener(this);
        mFindPassword.setOnClickListener(this);
        mSendCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_send_code:
                // 发送成功进入倒计时
                countDownTimer = new CountDownTimerUtils(mSendCode, 60000, 1000);
                countDownTimer.start();
                break;
            case R.id.btn_find_password:
                ResetPasswordActivity.startAction(ForgotPasswordActivity.this);
                finish();
                break;
            case R.id.back_imageView:
                ForgotPasswordActivity.this.finish();
                break;
            default:
        }
    }

}
