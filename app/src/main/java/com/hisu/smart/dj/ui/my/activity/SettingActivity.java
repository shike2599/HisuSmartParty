package com.hisu.smart.dj.ui.my.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.login.activity.LoginActivity;
import com.hisu.smart.dj.ui.main.activity.MainActivity;
import com.hisu.smart.dj.ui.web.activity.WebActivity;
import com.hisu.smart.dj.ui.widget.CommomDialog;
import com.hisu.smart.dj.ui.widget.ProfileEdit;
import com.hisu.smart.dj.utils.APKVersionCodeUtils;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.LoadingDialog;

import butterknife.Bind;

public class SettingActivity extends BaseActivity implements View.OnClickListener{
     @Bind(R.id.pe_userInfo)
     ProfileEdit  pe_userInfo;
     @Bind(R.id.pe_update_password)
     ProfileEdit pe_update_password;
     @Bind(R.id.pe_reset_password)
     ProfileEdit pe_reset_password;
     @Bind(R.id.pe_about_us)
     ProfileEdit pe_about_us;
     @Bind(R.id.edit_load_Btn)
     Button exit_Btn;
     @Bind(R.id.back_imageView)
     ImageView back_img;
     @Bind(R.id.title_TextView)
     TextView show_title;
     @Bind(R.id.version_TextView)
     TextView show_version;
     private CommomDialog commomDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initPresenter() {
        show_title.setText("设置");
        back_img.setOnClickListener(this);
        pe_userInfo.setOnClickListener(this);
        pe_update_password.setOnClickListener(this);
        pe_reset_password.setOnClickListener(this);
        pe_about_us.setOnClickListener(this);
        exit_Btn.setOnClickListener(this);
        commomDialog = new CommomDialog(this,R.style.dialog,"",new CommomDialog.OnCloseListener(){
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
            }
        });
        setIconKey();

    }
    private void setIconKey() {
        pe_userInfo.set(R.mipmap.set_user_info_icon, "个人信息");
        pe_update_password.set(R.mipmap.set_update_password_icon, "修改密码");
        pe_reset_password.set(R.mipmap.check_version_icon, "版本更新");
        pe_about_us.set(R.mipmap.set_aboutus_icon, "关于我们");

    }
    @Override
    public void initView() {
        String versionName = APKVersionCodeUtils.getVerName(this);
        show_version.setText("当前版本："+versionName);
    }

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pe_userInfo:
                MyInfoActivity.startAction(this);
                break;
            case R.id.pe_update_password:
                UpdatePassWordActivity.startAction(this);
                break;
            case R.id.pe_reset_password://版本更新
                commomDialog.isShowCancelBtn(false);
                commomDialog.setTitle("提示");
                commomDialog.setContent("当前为最新版本，无需更新!");
                commomDialog.show();
//                ResetPasswordActivity.startAction(this);
                break;
            case R.id.pe_about_us:
                AboutActivity.startAction(this);
                break;
            case R.id.edit_load_Btn:
                //删除密码
                AppConfig.getInstance().clearPassWord(AppConstant.USER_PASSWORD);
                LoginActivity.startAction(SettingActivity.this);
                SettingActivity.this.finish();
                break;
            case R.id.back_imageView:
                SettingActivity.this.finish();
                break;
        }
    }
}
