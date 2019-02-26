package com.hisu.smart.dj.ui.main.contract;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.NotingResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 * des:新闻列表contract
 * Created by xsf
 * on 2016.09.14:38
 */
public interface QrCodeToLoginContract {
    interface Model extends BaseModel {
        //请求获取新闻
        Observable <NotingResponse> qrcodeToLogin(Integer stbUserId, Integer stbType, Integer userId);
    }

    interface View extends BaseView {
        //返回获取的新闻
        void returnQrcodeToLoginData(NotingResponse notingResponse);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取新闻请求
        public abstract void qrcodeToLoginRequest(Integer stbUserId, Integer stbType, Integer userId);
    }

}
