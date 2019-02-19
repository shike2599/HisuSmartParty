package com.hisu.smart.dj.ui.my.contract;

import com.hisu.smart.dj.entity.NotingResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lichee on 2019/1/28.
 */

public interface UpdatePasswordContract {
    interface Model extends BaseModel {
        Observable<NotingResponse> changePassword(String userName, String  oldPwd, String  newPwd, String  phone);
    }

    interface View extends BaseView {
       void returnchangePasswordDate(NotingResponse baseResponse);
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void changePasswordRequest(String userName, String  oldPwd, String  newPwd, String  phone);
    }
}
