package com.hisu.smart.dj.ui.my.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.my.contract.ForgotPasswordContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lichee on 2019/1/28.
 */

public class ForgotPasswordModel implements ForgotPasswordContract.Model {
    @Override
    public Observable<BaseResponse> sendVerifyCode(String phone) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .sendVerifyCode(phone)
                .map(new Func1<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse call(BaseResponse baseResponse) {
                        return baseResponse;
                    }
                })
                .compose(RxSchedulers.<BaseResponse>io_main());
    }

    @Override
    public Observable<BaseResponse> verifyPhoneCode(String phone, String code) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .verifyPhoneCode(phone,code)
                .map(new Func1<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse call(BaseResponse baseResponse) {
                        return baseResponse;
                    }
                })
                .compose(RxSchedulers.<BaseResponse>io_main());
    }
}
