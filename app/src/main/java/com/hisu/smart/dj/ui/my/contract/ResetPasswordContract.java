package com.hisu.smart.dj.ui.my.contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import rx.Observable;

/**
 * Created by lichee on 2019/1/28.
 */

public interface ResetPasswordContract {
    interface Model extends BaseModel {
        Observable<BaseResponse> forgetPassword(String phone, String newPwd);
    }

    interface View extends BaseView {
       void returnforgetPassword(BaseResponse baseResponse);
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void forgetPasswordRequest(String phone, String code);
    }
}
