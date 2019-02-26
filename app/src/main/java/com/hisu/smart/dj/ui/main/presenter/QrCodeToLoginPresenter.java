package com.hisu.smart.dj.ui.main.presenter;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.main.contract.NewsListContract;
import com.hisu.smart.dj.ui.main.contract.QrCodeToLoginContract;
import com.jaydenxiao.common.baserx.RxSubscriber;


/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 */
public class QrCodeToLoginPresenter extends QrCodeToLoginContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void qrcodeToLoginRequest(Integer stbUserId, Integer stbType, Integer userId) {
        mRxManage.add(mModel.qrcodeToLogin(stbUserId,stbType,userId)
                .subscribe(new RxSubscriber<NotingResponse>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(null);
                    }

                    @Override
                    protected void _onNext(NotingResponse notingResponse) {
                        mView.returnQrcodeToLoginData(notingResponse);
                        mView.stopLoading(null);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,null);
                    }
                }));
    }
}
