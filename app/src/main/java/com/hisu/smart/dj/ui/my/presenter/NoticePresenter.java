package com.hisu.smart.dj.ui.my.presenter;

import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.ui.my.contract.NoticeContract;
import com.hisu.smart.dj.ui.my.contract.ResetPasswordContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * Created by lichee on 2019/1/29.
 */

public class NoticePresenter extends NoticeContract.Presenter {

    //通知消息内容
    @Override
    public void getListNoticeByTimeRequest(Integer userId, Integer partyBranchId, Integer id,String publishTime, Integer limitNum) {
        mRxManage.add(mModel.getListNoticeByTime(userId,partyBranchId,id,publishTime,limitNum)
                .subscribe(new RxSubscriber<NoticeInfoEntity>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(null);
                    }
                    @Override
                    protected void _onNext(NoticeInfoEntity noticeInfoEntity) {
                        mView.returnListNoticeByTime(noticeInfoEntity,null);
                        mView.showLoading(null);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,null);
                    }
                }));
    }
}
