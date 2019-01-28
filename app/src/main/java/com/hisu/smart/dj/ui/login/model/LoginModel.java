package com.hisu.smart.dj.ui.login.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;

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
    public Observable<BaseResponse<LoginUserEntity>> getLoginResponse(String username, String password) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .login(username,password)
                .map(new Func1<BaseResponse<LoginUserEntity>, BaseResponse<LoginUserEntity>>() {
                    @Override
                    public BaseResponse<LoginUserEntity> call(BaseResponse<LoginUserEntity> loginUserResponse) {
                        return loginUserResponse;
                    }
                }).compose(RxSchedulers.<BaseResponse<LoginUserEntity>>io_main());
    }
}
