package com.hisu.smart.dj.ui.login.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;

import com.hisu.smart.dj.entity.LoginResponse;
import com.hisu.smart.dj.entity.LoginUserEntity;
import com.hisu.smart.dj.ui.login.contract.LoginContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;

/**
 *
 * @author lichee
 * @date 2019/1/28
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginResponse> getLoginResponse(String username, String password) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .login(username,password)
                .map(new Func1<LoginResponse, LoginResponse>() {
                    @Override
                    public LoginResponse call(LoginResponse loginUserResponse) {
                        LoginUserEntity userEntity = loginUserResponse.getData();
                        userEntity.setPhoto(userEntity.getOutServer()+userEntity.getPhoto());
                        return loginUserResponse;
                    }
                }).compose(RxSchedulers.<LoginResponse>io_main());
    }
}
