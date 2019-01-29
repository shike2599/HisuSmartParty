package com.hisu.smart.dj.ui.login.contract;

import com.hisu.smart.dj.entity.LoginResponse;

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
    }
    interface View extends BaseView {
        void returnLoginResponse(LoginResponse loginResponse);
    }
    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void loginResponseRequest(String username,String password);
    }
}
