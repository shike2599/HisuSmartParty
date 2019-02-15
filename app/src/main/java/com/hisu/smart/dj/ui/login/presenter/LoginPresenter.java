package com.hisu.smart.dj.ui.login.presenter;

import com.hisu.smart.dj.entity.LoginResponse;

import com.hisu.smart.dj.entity.MemberInfoResponse;
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
        mRxManage.add(mModel.getLoginResponse(username,password).subscribe(new RxSubscriber<LoginResponse>(mContext,false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("Login");
            }

            @Override
            protected void _onNext(LoginResponse loginResponse) {
                mView.returnLoginResponse(loginResponse);
                mView.stopLoading("Login");
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"Login");
            }
        }));
    }

    @Override
    public void MemberInfoResponseRequest(Integer userId) {
        mRxManage.add(mModel.getMemberInfoResponse(userId).subscribe(new RxSubscriber<MemberInfoResponse>(mContext,false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("MemberInfo");
            }

            @Override
            protected void _onNext(MemberInfoResponse memberInfoResponse) {
                mView.returnMemberInfoResponse(memberInfoResponse);
                mView.stopLoading("MemberInfo");
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"MemberInfo");
            }
        }));
    }


}
