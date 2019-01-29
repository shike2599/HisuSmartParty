package com.hisu.smart.dj.ui.my.contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import rx.Observable;

/**
 * Created by lichee on 2019/1/28.
 */

public interface ForgotPasswordContract {
    interface Model extends BaseModel {
        Observable<BaseResponse> sendVerifyCode(String phone);
        Observable<BaseResponse> verifyPhoneCode(String phone, String code);
    }

    interface View extends BaseView {
       void returnSendVerifyCode(BaseResponse baseResponse);
       void returnVerifyPhoneCode(BaseResponse baseResponse);
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void sendVerifyCode(String phone);
        public abstract void verifyPhoneCode(String phone, String code);
    }
}
