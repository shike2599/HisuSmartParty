package com.hisu.smart.dj.ui.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.LoginResponse;
import com.hisu.smart.dj.entity.LoginUserEntity;
import com.hisu.smart.dj.ui.login.contract.LoginContract;
import com.hisu.smart.dj.ui.login.model.LoginModel;
import com.hisu.smart.dj.ui.login.presenter.LoginPresenter;
import com.hisu.smart.dj.ui.main.activity.MainActivity;
import com.hisu.smart.dj.ui.my.activity.ForgotPasswordActivity;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import butterknife.Bind;

/**
 * @author lichee
 */
public class LoginActivity extends BaseActivity<LoginPresenter,LoginModel> implements View.OnClickListener,LoginContract.View {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_forget_password)
    TextView tvForget;
    @Bind(R.id.loaded_login)
    LoadingTip loadingTip;

    private String username;
    private String password;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        btnLogin.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        username = AppConfig.getInstance().getString(AppConstant.USER_NAME,"");
        password = AppConfig.getInstance().getString(AppConstant.USER_PASSWORD,"");
        etName.setText(username);
        etPassword.setText(password);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_login:
                 if(checkForm()){
                     mPresenter.loginResponseRequest(username,password);
                     return;
                 }
                break;
            case R.id.tv_forget_password:
                ForgotPasswordActivity.startAction(LoginActivity.this);
                break;
            default:
                return;
        }
    }

    private boolean checkForm() {
        username = etName.getText().toString();
        password = etPassword.getText().toString();
        if("".equals(username)){
            ToastUitl.show("用户名不能为空", Toast.LENGTH_SHORT);
            return false;
        }
        if("".equals(password)){
            ToastUitl.show("密码不能为空", Toast.LENGTH_SHORT);
            return false;
        }
        if(password.length() < 6){
            ToastUitl.show("密码不能小于6位", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    @Override
    public void showLoading(String tag) {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading(String tag) {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.sereverError);
    }

    @Override
    public void returnLoginResponse(LoginResponse loginResponse) {
        LogUtils.logd("returnLoginResponse:"+loginResponse.toString());
        if(AppConstant.REQUEST_SUCCESS.equals(loginResponse.getResultCode())){
            LoginUserEntity entity = loginResponse.getData();
            //保存当前用户登陆状态
            AppConfig.getInstance().setInt(AppConstant.USER_ID,entity.getUserId());
            AppConfig.getInstance().setString(AppConstant.USER_NAME,entity.getUserName());
            AppConfig.getInstance().setString(AppConstant.NICK_NAME,entity.getNickname());
            AppConfig.getInstance().setString(AppConstant.USER_PHOTO,entity.getPhoto());
            AppConfig.getInstance().setString(AppConstant.USER_PHONE,entity.getPhone());
            AppConfig.getInstance().setString(AppConstant.USER_PASSWORD,password);
            AppConfig.getInstance().setBoolean(AppConstant.IS_PARTY_MEMBER,entity.isIsPartyMember());
            AppConfig.getInstance().setBoolean(AppConstant.IS_PARTY_BRANCH,entity.isIsPartyBranch());
            AppConfig.getInstance().setBoolean(AppConstant.IS_PARTY_COMMITTEE,entity.isIsPartyCommittee());
            MainActivity.startAction(LoginActivity.this);
            finish();
        }else{
            ToastUitl.show(loginResponse.getResultDesc(),Toast.LENGTH_SHORT);
        }
    }
}
