package com.hisu.smart.dj.ui.my.presenter;

import com.hisu.smart.dj.ui.my.contract.ForgotPasswordContract;
import com.hisu.smart.dj.ui.my.contract.ResetPasswordContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * Created by lichee on 2019/1/29.
 */

public class ResetPasswordPresenter extends ResetPasswordContract.Presenter {

    @Override
    public void forgetPasswordRequest(String phone, String newPwd) {
        mRxManage.add(mModel.forgetPassword(phone,newPwd).subscribe(new RxSubscriber<BaseResponse>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(null);
            }

            @Override
            protected void _onNext(BaseResponse baseResponse) {
                mView.stopLoading(null);
                mView.returnforgetPassword(baseResponse);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }
}
