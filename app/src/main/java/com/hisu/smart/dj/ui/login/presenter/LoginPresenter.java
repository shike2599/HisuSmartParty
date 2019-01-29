package com.hisu.smart.dj.ui.login.presenter;

import com.hisu.smart.dj.entity.LoginResponse;

import com.hisu.smart.dj.ui.login.contract.LoginContract;

import com.jaydenxiao.common.baserx.RxSubscriber;


/**
 *
 * @author lichee
 * @date 2019/1/28
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void loginResponseRequest(String username, String password) {
      mRxManage.add(mModel.getLoginResponse(username,password).subscribe(new RxSubscriber<LoginResponse>(mContext,true) {

          @Override
          public void onStart() {
              super.onStart();
          }

          @Override
          protected void _onNext(LoginResponse loginResponse) {
            mView.returnLoginResponse(loginResponse);
          }

          @Override
          protected void _onError(String message) {
              mView.showErrorTip(message,"");
          }
      }));
    }
}
