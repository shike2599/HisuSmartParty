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
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.login.activity.LoginActivity;
import com.hisu.smart.dj.ui.my.contract.UpdatePasswordContract;
import com.hisu.smart.dj.ui.my.model.UpdatePasswordModel;
import com.hisu.smart.dj.ui.my.presenter.UpdatePasswordPresenter;
import com.hisu.smart.dj.ui.widget.CommomDialog;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.LoadingDialog;

import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * @author lichee
 */
public class UpdatePassWordActivity extends BaseActivity<UpdatePasswordPresenter,UpdatePasswordModel>
        implements View.OnClickListener, UpdatePasswordContract.View{

    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView back_img;//返回按钮
    @Bind(R.id.btn_modify_password)
    Button start_update_Btn;
    @Bind(R.id.et_modify_old_password)
    EditText old_Pwd_Edt;
    @Bind(R.id.et_modify_new_password)
    EditText new_Pwd_Edt;
    @Bind(R.id.et_modify_new_password_again)
    EditText again_new_Pwd_Edt;
    private CommomDialog commomDialog;
    private String new_Pwd;
    private String userName;
    private String phone;
    private String old_save_Pwd;
    private boolean isUpdateSuc;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
        isUpdateSuc = false;
        userName = AppConfig.getInstance().getString(AppConstant.USER_NAME,"");
        old_save_Pwd = AppConfig.getInstance().getString(AppConstant.USER_PASSWORD,"");
        phone = AppConfig.getInstance().getString(AppConstant.USER_PHONE,"");
        commomDialog = new CommomDialog(this,R.style.dialog,"",new CommomDialog.OnCloseListener(){
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void initView() {
        mTitle.setText("修改密码");
        back_img.setOnClickListener(this);
        start_update_Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_imageView:
                UpdatePassWordActivity.this.finish();
                break;
            case R.id.btn_modify_password:
                String  old_Pwd = old_Pwd_Edt.getText().toString().trim();
                String  new_Pwd_once = new_Pwd_Edt.getText().toString().trim();
                new_Pwd = again_new_Pwd_Edt.getText().toString().trim();
                if(old_Pwd == null || old_Pwd.equals("")){
                    commomDialog.setTitle("提示");
                    commomDialog.isShowCancelBtn(false);
                    commomDialog.setContent("请输入旧密码!");
                    commomDialog.show();
                    return;
                }else{
                    if(!old_Pwd.equals(old_save_Pwd)){
                        commomDialog.setTitle("提示");
                        commomDialog.isShowCancelBtn(false);
                        commomDialog.setContent("原密码错误，请重新输入");
                        commomDialog.show();
                        return;
                    }
                }
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
                if(old_Pwd!=null&&new_Pwd_once!=null&&new_Pwd!=null){
                    if(new_Pwd_once.equals(new_Pwd)){
                        mPresenter.changePasswordRequest(userName,old_Pwd,new_Pwd,phone);
                    }else{
                        commomDialog.setTitle("提示");
                        commomDialog.isShowCancelBtn(false);
                        commomDialog.setContent("您两次输入的新密码不一致，请重新输入");
                        commomDialog.show();
                    }
                }
                break;

            default:
                return;
        }
    }

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, UpdatePassWordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void returnchangePasswordDate(NotingResponse baseResponse) {
        int resultCode = baseResponse.getResultCode();
        if(resultCode == 200){
//            isUpdateSuc = true;
//            commomDialog.setTitle("提示");
//            commomDialog.isShowCancelBtn(true);
//            commomDialog.setContent("密码修改成功!");
//            commomDialog.show();
            Toast.makeText(UpdatePassWordActivity.this,"密码修改成功，请重新登录",Toast.LENGTH_LONG).show();
            AppConfig.getInstance().clearPassWord(AppConstant.USER_PASSWORD);//删除就密码
            AppConfig.getInstance().deleteThreadData(userName);
            Intent intent = new Intent();
            intent.setClass(UpdatePassWordActivity.this,LoginActivity.class);
            startActivity(intent);
            UpdatePassWordActivity.this.finish();
        }
    }

    @Override
    public void showLoading(String tag) {
        LoadingDialog.showDialogForLoading(UpdatePassWordActivity.this,"正在修改中...",true);
    }

    @Override
    public void stopLoading(String tag) {
       LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        LoadingDialog.cancelDialogForLoading();
        commomDialog.setTitle("提示");
        commomDialog.isShowCancelBtn(false);
        commomDialog.setContent(msg);
        commomDialog.show();
    }

    /**
     * 校验密码
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    private  boolean isPassword(String password) {
        boolean flag = true;
        if(password.length()>6){
          flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    private  boolean validatePhonePass(String pass) {
//        String passRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$|";
        String passRegex = "^[0-9a-zA-Z]{6,12}$";
        return !TextUtils.isEmpty(pass) && pass.matches(passRegex);
    }

}
