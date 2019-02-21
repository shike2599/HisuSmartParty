package com.hisu.smart.dj.ui.my.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;

import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.login.activity.LoginActivity;
import com.hisu.smart.dj.ui.my.contract.ResetPasswordContract;
import com.hisu.smart.dj.ui.my.model.ResetPasswordModel;
import com.hisu.smart.dj.ui.my.presenter.ResetPasswordPresenter;
import com.hisu.smart.dj.ui.widget.CommomDialog;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.commonwidget.LoadingDialog;

import butterknife.Bind;

/**
 * @author lichee
 */
public class ResetPasswordActivity extends
        BaseActivity<ResetPasswordPresenter,ResetPasswordModel> implements
        View.OnClickListener,ResetPasswordContract.View {

    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView back_img;//返回按钮
    @Bind(R.id.btn_reset_password)
    Button start_reset;
    @Bind(R.id.et_new_password)
    EditText new_Pwd_Edt;
    @Bind(R.id.et_new_password_again)
    EditText new_Pwd_again_Edt;
    private CommomDialog commomDialog;
    private String new_Pwd;
    private boolean isSuccess;
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, ResetPasswordActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
        commomDialog = new CommomDialog(this,R.style.dialog,"",new CommomDialog.OnCloseListener(){
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if(isSuccess){
                    if(confirm){
                        dialog.dismiss();
                        isSuccess = false;
                        LoginActivity.startAction(ResetPasswordActivity.this);
                        ResetPasswordActivity.this.finish();
                    }
                }else{
                    dialog.dismiss();
                    isSuccess = false;
                }

            }
        });
    }

    @Override
    public void initView() {
        mTitle.setText("重置密码");
        back_img.setOnClickListener(this);
        start_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imageView:
                ResetPasswordActivity.this.finish();
                break;
            case R.id.btn_reset_password:
                String new_Pwd_once = new_Pwd_Edt.getText().toString().trim();
                new_Pwd = new_Pwd_again_Edt.getText().toString().trim();
                if(new_Pwd_once == null || new_Pwd_once.equals("")){
                    commomDialog.setTitle("提示");
                    commomDialog.isShowCancelBtn(false);
                    commomDialog.setContent("请输入新密码！");
                    commomDialog.show();
                    return;
                }else{
                    if(!validatePhonePass(new_Pwd_once)){
                        commomDialog.setTitle("提示");
                        commomDialog.isShowCancelBtn(false);
                        commomDialog.setContent("请输入6-12位数字或字母组合！");
                        commomDialog.show();
                        return;
                    }
                }
                if(new_Pwd == null || new_Pwd.equals("")){
                    commomDialog.setTitle("提示");
                    commomDialog.isShowCancelBtn(false);
                    commomDialog.setContent("请再次输入新密码！");
                    commomDialog.show();
                    return;
                }else{
                    if(!validatePhonePass(new_Pwd)){
                        commomDialog.setTitle("提示");
                        commomDialog.isShowCancelBtn(false);
                        commomDialog.setContent("请输入6-12位数字或字母组合！");
                        commomDialog.show();
                        return;
                    }
                }

                if(new_Pwd_once!=null&&new_Pwd!=null){
                    if(new_Pwd_once.equals(new_Pwd)){
                        mPresenter.forgetPasswordRequest(AppConstant.RESET_PWD_PHONE,new_Pwd);
                    }else{
                        commomDialog.setTitle("提示");
                        commomDialog.isShowCancelBtn(false);
                        commomDialog.setContent("您两次输入的新密码不一致，请重新输入");
                        commomDialog.show();
                    }
                }
                break;
            default:
        }
    }

    @Override
    public void returnforgetPassword(BaseResponse baseResponse) {
        if(baseResponse.getResultCode().equals(AppConstant.REQUEST_SUCCESS)){
            commomDialog.setTitle("提示");
            commomDialog.isShowCancelBtn(false);
            commomDialog.setContent("密码重置成功!");
            commomDialog.show();
            isSuccess = true;
        }else{
            commomDialog.setTitle("提示");
            commomDialog.isShowCancelBtn(false);
            commomDialog.setContent("重置密码失败");
            commomDialog.show();
        }
    }

    @Override
    public void showLoading(String tag) {
        LoadingDialog.showDialogForLoading(this,"正在重置中，请稍等...",false);
    }

    @Override
    public void stopLoading(String tag) {
        LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        LoadingDialog.cancelDialogForLoading();
    }

    private  boolean validatePhonePass(String pass) {
//        String passRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$|";
        String passRegex = "^[0-9a-zA-Z]{6,12}$";
        return !TextUtils.isEmpty(pass) && pass.matches(passRegex);
    }
}
