package com.hisu.smart.dj.ui.main.model;

import android.util.Log;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.main.contract.NewsListContract;
import com.hisu.smart.dj.ui.main.contract.QrCodeToLoginContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;


/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 */
public class QrCodeToLoginModel implements QrCodeToLoginContract.Model {
    @Override
    public Observable<NotingResponse> qrcodeToLogin(Integer stbUserId, Integer stbType, Integer userId) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .qrcodeToLogin(stbUserId,stbType,userId)
                .map(new Func1<NotingResponse, NotingResponse>() {
                    @Override
                    public NotingResponse call(NotingResponse notingResponse) {
                        return notingResponse;
                    }
                })
                .compose(RxSchedulers.<NotingResponse>io_main());
    }
}
