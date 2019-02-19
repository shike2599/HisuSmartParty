package com.hisu.smart.dj.ui.my.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.my.activity.UpdatePassWordActivity;
import com.hisu.smart.dj.ui.my.contract.ForgotPasswordContract;
import com.hisu.smart.dj.ui.my.contract.UpdatePasswordContract;
import com.hisu.smart.dj.ui.study.contract.UpLoadFileContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lichee on 2019/1/28.
 */

public class UpdatePasswordModel implements UpdatePasswordContract.Model {
    @Override
    public Observable<NotingResponse> changePassword(String userName, String oldPwd, String newPwd, String phone) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .changePassword(userName,oldPwd,newPwd,phone)
                .map(new Func1<NotingResponse, NotingResponse>() {
                    @Override
                    public NotingResponse call(NotingResponse notingResponse) {
                        return notingResponse;
                    }
                })
                .compose(RxSchedulers.<NotingResponse>io_main());
    }
}
