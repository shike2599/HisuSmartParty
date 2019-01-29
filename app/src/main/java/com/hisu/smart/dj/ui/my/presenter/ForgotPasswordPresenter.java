package com.hisu.smart.dj.ui.my.presenter;

import com.hisu.smart.dj.ui.my.contract.ForgotPasswordContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * Created by lichee on 2019/1/29.
 */

public class ForgotPasswordPresenter extends ForgotPasswordContract.Presenter {
    @Override
    public void sendVerifyCode(String phone) {
        mRxManage.add(mModel.sendVerifyCode(phone).subscribe(new RxSubscriber<BaseResponse>(mContext,false) {
            @Override
            protected void _onNext(BaseResponse baseResponse) {
                mView.returnSendVerifyCode(baseResponse);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }

    @Override
    public void verifyPhoneCode(String phone, String code) {
        mRxManage.add(mModel.verifyPhoneCode(phone,code).subscribe(new RxSubscriber<BaseResponse>(mContext,false) {
            @Override
            protected void _onNext(BaseResponse baseResponse) {
                mView.returnVerifyPhoneCode(baseResponse);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }
}
