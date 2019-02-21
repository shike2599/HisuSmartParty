package com.hisu.smart.dj.ui.my.activity;

import android.app.Activity;
import android.content.Intent;

import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;

import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.my.contract.ForgotPasswordContract;
import com.hisu.smart.dj.ui.my.model.ForgotPasswordModel;
import com.hisu.smart.dj.ui.my.presenter.ForgotPasswordPresenter;
import com.hisu.smart.dj.utils.CountDownTimerUtils;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.commonutils.ToastUitl;

import butterknife.Bind;

/**
 * @author lichee
 */
public class ForgotPasswordActivity extends BaseActivity<ForgotPasswordPresenter,ForgotPasswordModel> implements View.OnClickListener , ForgotPasswordContract.View{

    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView back_img;//返回按钮
    @Bind(R.id.btn_find_password)
    Button mFindPassword;
    @Bind(R.id.tv_send_code)
    TextView mSendCode;
    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_yzcode)
    EditText et_yzcode;
    CountDownTimerUtils countDownTimer;
    private String phone;
    private String yzcode;

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
        mPresenter.setVM(this,mModel);
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
                if(isMobileNumber()){
                    mPresenter.sendVerifyCode(phone);
                    // 发送成功进入倒计时
                    countDownTimer = new CountDownTimerUtils(mSendCode, 60000, 1000);
                    countDownTimer.start();
                }
                break;
            case R.id.btn_find_password:
                if(checkForm()){
                    mPresenter.verifyPhoneCode(phone,yzcode);
                }
                break;
            case R.id.back_imageView:
                ForgotPasswordActivity.this.finish();
                break;
            default:
        }
    }

    @Override
    public void showLoading(String tag) {

    }

    @Override
    public void stopLoading(String tag) {

    }

    @Override
    public void showErrorTip(String msg, String tag) {
        ToastUitl.show(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void returnSendVerifyCode(BaseResponse baseResponse) {
        String code = baseResponse.getResultCode();
        if(!"200".equals(code)){
            ToastUitl.show(baseResponse.getResultDesc(), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void returnVerifyPhoneCode(BaseResponse baseResponse) {
        String code = baseResponse.getResultCode();
        if(!"200".equals(code)){
            ToastUitl.show(baseResponse.getResultDesc(), Toast.LENGTH_SHORT);
        }else {
            AppConstant.RESET_PWD_PHONE = phone;
            ResetPasswordActivity.startAction(ForgotPasswordActivity.this);
        }
    }

    public boolean isMobileNumber() {
        phone = et_phone.getText().toString();
        String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        if(TextUtils.isEmpty(phone)){
            ToastUitl.show("手机号码不能为空",Toast.LENGTH_SHORT);
            return false;
        }
        if(!phone.matches(telRegex)){
            ToastUitl.show("请输入正确的手机号码",Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private boolean checkForm() {
         if(!isMobileNumber()){
             return false;
         }
        yzcode = et_yzcode.getText().toString();
         if(TextUtils.isEmpty(yzcode)){
             ToastUitl.show("验证码不能为空",Toast.LENGTH_SHORT);
             return false;
         }
         if(yzcode.length() < 6){
             ToastUitl.show("请输入正确的验证码",Toast.LENGTH_SHORT);
             return false;
         }
         return true;
    }
}
