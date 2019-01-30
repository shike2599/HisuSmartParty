package com.hisu.smart.dj.ui.login.activity;

import android.text.TextUtils;
import android.util.Log;
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
import com.hisu.smart.dj.entity.MemberInfoResponse;
import com.hisu.smart.dj.ui.login.contract.LoginContract;
import com.hisu.smart.dj.ui.login.model.LoginModel;
import com.hisu.smart.dj.ui.login.presenter.LoginPresenter;
import com.hisu.smart.dj.ui.main.activity.MainActivity;
import com.hisu.smart.dj.ui.my.activity.ForgotPasswordActivity;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingDialog;
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
        //etName.setText(username);
        //etPassword.setText(password);
        if(!TextUtils.isEmpty(username)){
            MainActivity.startAction(LoginActivity.this);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_login:
                 if(checkForm()){
                     LoadingDialog.showDialogForLoading(
                             LoginActivity.this,"正在登录中。。。",true);
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
       LoadingDialog.cancelDialogForLoading();
        ToastUitl.show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void returnLoginResponse(LoginResponse loginResponse) {
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
//            MainActivity.startAction(LoginActivity.this);
//            finish();
//            mPresenter.loginResponseRequest(username,password);
            //登录成功后，开始拿党员信息
            mPresenter.MemberInfoResponseRequest(entity.getUserId());
        }else{
            ToastUitl.show(loginResponse.getResultDesc(),Toast.LENGTH_SHORT);
        }
    }
    //党员信息查询返回
    @Override
    public void returnMemberInfoResponse(MemberInfoResponse memberInfoResponse) {
//        Log.d("LoginMemberInfo","memberInfoResponse--code == "+memberInfoResponse.getResultCode());
        String code = String.valueOf(memberInfoResponse.getResultCode());
        //返回200
        if(AppConstant.REQUEST_SUCCESS.equals(code)){
//            Log.d("LoginMemberInfo","memberInfoResponse--");
            LoadingDialog.cancelDialogForLoading();
            MemberInfoResponse.DataBean memberInfo = memberInfoResponse.getData();
            AppConfig.getInstance().setInt(AppConstant.MEMBER_ID,memberInfo.getId()); //党员序号
            AppConfig.getInstance().setString(AppConstant.MEMBER_NAME,memberInfo.getName()); //党员名称
            AppConfig.getInstance().setString(AppConstant.MEMBER_CODE,memberInfo.getCode()); //党员编号
            AppConfig.getInstance().setString(AppConstant.MEMBER_PHONE,memberInfo.getPhone()); //联系电话
            AppConfig.getInstance().setString(AppConstant.MEMBER_IDCARD,memberInfo.getIdCard()); //身份证
            AppConfig.getInstance().setInt(AppConstant.MEMBER_SEX,memberInfo.getSex()); //性别 0-女 1-男
            AppConfig.getInstance().setInt(AppConstant.MEMBER_PARTYBRANCH_ID,memberInfo.getPartyBranchId()); //支部序号
            AppConfig.getInstance().setInt(AppConstant.MEMBER_STATUS,memberInfo.getStatus()); //党员性质 0-在职 1-退休 2-农民
            AppConfig.getInstance().setInt(AppConstant.MEMBER_INTEGRAL,memberInfo.getIntegral()); //积分
            MainActivity.startAction(LoginActivity.this);
            finish();
        }
    }
}
