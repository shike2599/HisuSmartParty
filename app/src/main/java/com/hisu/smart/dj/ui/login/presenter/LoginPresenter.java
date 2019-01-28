package com.hisu.smart.dj.ui.login.presenter;

import com.hisu.smart.dj.entity.LoginUserEntity;
import com.hisu.smart.dj.ui.login.contract.LoginContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 *
 * @author lichee
 * @date 2019/1/28
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void loginResponseRequest(String username, String password) {
      mRxManage.add(mModel.getLoginResponse(username,password).subscribe(new RxSubscriber<BaseResponse<LoginUserEntity>>(mContext,false) {

          @Override
          public void onStart() {
              super.onStart();
              mView.showLoading("");
          }

          @Override
          protected void _onNext(BaseResponse<LoginUserEntity> loginResponse) {
            mView.returnLoginResponse(loginResponse);
            mView.stopLoading("");
          }

          @Override
          protected void _onError(String message) {

          }
      }));
    }
}
