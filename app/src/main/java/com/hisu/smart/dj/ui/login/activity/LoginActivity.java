package com.hisu.smart.dj.ui.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.LoginUserEntity;
import com.hisu.smart.dj.ui.login.contract.LoginContract;
import com.hisu.smart.dj.ui.login.model.LoginModel;
import com.hisu.smart.dj.ui.login.presenter.LoginPresenter;
import com.hisu.smart.dj.ui.main.activity.MainActivity;
import com.hisu.smart.dj.ui.my.activity.ForgotPasswordActivity;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;

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

    }

    @Override
    public void stopLoading(String tag) {

    }

    @Override
    public void showErrorTip(String msg, String tag) {

    }

    @Override
    public void returnLoginResponse(BaseResponse<LoginUserEntity> loginResponse) {
        LogUtils.logd(loginResponse.toString());
        if("200".equals(loginResponse.getResultCode())){
            MainActivity.startAction(LoginActivity.this);
            finish();
        }else{
            ToastUitl.show(loginResponse.getResultDesc(),Toast.LENGTH_SHORT);
        }
    }
}
