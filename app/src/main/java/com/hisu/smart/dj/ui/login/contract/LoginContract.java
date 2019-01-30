package com.hisu.smart.dj.ui.login.contract;

import com.hisu.smart.dj.entity.LoginResponse;

import com.hisu.smart.dj.entity.MemberInfoResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 *
 * @author lichee
 * @date 2019/1/28
 */

public interface LoginContract  {
    interface Model extends BaseModel {
        Observable<LoginResponse> getLoginResponse(String username, String password);
        Observable<MemberInfoResponse> getMemberInfoResponse(Integer userId);
    }
    interface View extends BaseView {
        void returnLoginResponse(LoginResponse loginResponse);
        void returnMemberInfoResponse(MemberInfoResponse memberInfoResponse);
    }
    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void loginResponseRequest(String username,String password);
        public abstract void MemberInfoResponseRequest(Integer userId);
    }

}
