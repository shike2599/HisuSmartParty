package com.hisu.smart.dj.ui.login.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppApplication;
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
import com.hisu.smart.dj.ui.widget.CommomDialog;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.baseapp.AppManager;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingDialog;

import java.util.List;

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
    @Bind(R.id.show_listPopWindow_img)
    ImageView show_list_pop;

    private String username;
    private String password;
    private CommomDialog commomDialog;
    private ListPopupWindow mListPop;
    private List<String> lists;
    private String save_username;
    private String save_password;
    private boolean isExit = false;



    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        //添加Activity到堆栈
        mPresenter.setVM(this,mModel);
        commomDialog = new CommomDialog(this,R.style.dialog,"",new CommomDialog.OnCloseListener(){
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if(isExit){
                    if(confirm){
                        dialog.dismiss();
                        isExit = false;
                        AppManager.getAppManager().AppExit(LoginActivity.this,true);
                    }else{
                        isExit = false;
                        dialog.dismiss();
                    }
                }else {
                    dialog.dismiss();
                    isExit = false;
                    LoadingDialog.cancelDialogForLoading();
                }

                }
        });
        commomDialog.isShowCancelBtn(false);
        mListPop = new ListPopupWindow(this);
        lists = AppConfig.getInstance().getAllData();
        if(lists!=null&&lists.size()>0){
            save_username = AppConfig.getInstance().getString(AppConstant.USER_NAME,"");
            save_password = AppConfig.getInstance().getUserNameAndPassWordString(save_username,"");
            mListPop.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lists));

        }
    }

    @Override
    public void initView() {
        btnLogin.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        show_list_pop.setOnClickListener(this);

        etName.setText(save_username);
        etPassword.setText(save_password);

        username = AppConfig.getInstance().getString(AppConstant.USER_NAME,"");
        password = AppConfig.getInstance().getString(AppConstant.USER_PASSWORD,"");

        mListPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListPop.setAnchorView(etName);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListPop.setModal(true);//设置是否是模式
        mListPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                etName.setText(lists.get(position));
                etPassword.setText(AppConfig.getInstance().getUserNameAndPassWordString(lists.get(position),""));
                mListPop.dismiss();
                show_list_pop.setBackgroundResource(R.mipmap.list_pop_bottom_icon);
            }
        });
        //监听PopWindow的显示和隐藏
        mListPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                show_list_pop.setBackgroundResource(R.mipmap.list_pop_bottom_icon);
            }
        });

        if(!TextUtils.isEmpty(password)){
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
                             LoginActivity.this,"正在登录中...",true);
                     mPresenter.loginResponseRequest(username,password);
                     return;
                 }
                break;
            case R.id.tv_forget_password:
                ForgotPasswordActivity.startAction(LoginActivity.this);
                break;
            case R.id.show_listPopWindow_img:
                show_list_pop.setBackgroundResource(R.mipmap.list_pop_top_icon);
                mListPop.show();
                break;
            default:
                return;
        }
    }

    private boolean checkForm() {
        username = etName.getText().toString();
        password = etPassword.getText().toString();
        if("".equals(username)){
            ToastUitl.show("请输入用户名", Toast.LENGTH_SHORT);
            return false;
        }
        if("".equals(password)){
            ToastUitl.show("请输入密码", Toast.LENGTH_SHORT);
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
        Log.d("LoginActivity","-----showLoading----");
//        LoadingDialog.showDialogForLoading(
//                LoginActivity.this,"正在登录中...",true);
    }

    @Override
    public void stopLoading(String tag) {
//        LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showErrorTip(String msg, String tag) {
       LoadingDialog.cancelDialogForLoading();
//        ToastUitl.show(msg,Toast.LENGTH_SHORT);
        commomDialog.setTitle("提示");
        commomDialog.setContent(msg);
        commomDialog.show();
    }

    @Override
    public void returnLoginResponse(LoginResponse loginResponse) {
        Log.d("LoginActivity","ResultCode()---"+loginResponse.getResultCode());
        if(AppConstant.REQUEST_SUCCESS.equals(loginResponse.getResultCode())){
            LoginUserEntity entity = loginResponse.getData();

            if(entity.isIsPartyMember()){
            //保存用户名和密码，用户用户选择
            if(lists!=null){
                String isSavedStr = AppConfig.getInstance().getUserNameAndPassWordString(username,"");
                Log.d("LoginActivity","lists-==size---"+lists.size());
                //查重
                if(isSavedStr.equals("")){
                    if(lists.size()>=3){
                        Log.d("LoginActivity","大于三了准备删除==="+lists.get(2));
                        AppConfig.getInstance().deleteThreadData(lists.get(0));
                    }
                    AppConfig.getInstance().setUserNameAndPassWordString(username,password);
                }
            }else{
                //集合为空说明是第一次保存
                AppConfig.getInstance().setUserNameAndPassWordString(username,password);
            }
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
//            MainActivity.startAction(IactiveLoginActivity.this);
//            finish();
//            mPresenter.loginResponseRequest(username,password);
            //登录成功后，开始拿党员信息
              mPresenter.MemberInfoResponseRequest(entity.getUserId());
            }else{
                commomDialog.setTitle("提示");
                commomDialog.setContent("用户名或密码错误，请重新输入!");
                commomDialog.show();
            }

        }else{
//            ToastUitl.show(loginResponse.getResultDesc(),Toast.LENGTH_SHORT);
            commomDialog.setTitle("提示");
            commomDialog.setContent("用户名或密码错误，请重新输入!");
            commomDialog.show();
        }
    }
    //党员信息查询返回
    @Override
    public void returnMemberInfoResponse(MemberInfoResponse memberInfoResponse) {
        LoadingDialog.cancelDialogForLoading();
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
            LoginActivity.this.finish();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            commomDialog.isShowCancelBtn(true);
            commomDialog.setTitle("提示");
            commomDialog.setContent("您确定要退出智慧党建吗？");
            commomDialog.show();
            isExit = true;
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
