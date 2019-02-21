package com.hisu.smart.dj.ui.my.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.my.contract.ForgotPasswordContract;
import com.hisu.smart.dj.ui.my.contract.ResetPasswordContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lichee on 2019/1/28.
 */

public class ResetPasswordModel implements ResetPasswordContract.Model {
    @Override
    public Observable<BaseResponse> forgetPassword(String phone, String newPwd) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .forgetPassword(phone,newPwd)
                .map(new Func1<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse call(BaseResponse baseResponse) {
                        return baseResponse;
                    }
                })
                .compose(RxSchedulers.<BaseResponse>io_main());
    }
}
