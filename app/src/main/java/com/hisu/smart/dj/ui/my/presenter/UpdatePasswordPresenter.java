package com.hisu.smart.dj.ui.my.presenter;

import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.my.contract.ForgotPasswordContract;
import com.hisu.smart.dj.ui.my.contract.UpdatePasswordContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * Created by lichee on 2019/1/29.
 */

public class UpdatePasswordPresenter extends UpdatePasswordContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void changePasswordRequest(String userName, String oldPwd, String newPwd, String phone) {
        mRxManage.add(mModel.changePassword(userName,oldPwd,newPwd,phone).subscribe(new RxSubscriber<NotingResponse>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(null);
            }

            @Override
            protected void _onNext(NotingResponse notingResponse) {
                mView.stopLoading(null);
                mView.returnchangePasswordDate(notingResponse);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }
}
