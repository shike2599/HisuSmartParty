package com.hisu.smart.dj.ui.my.presenter;

import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.my.contract.NoticeContract;
import com.hisu.smart.dj.ui.my.contract.NoticeReadContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * Created by lichee on 2019/1/29.
 */

public class NoticeReadPresenter extends NoticeReadContract.Presenter {
    @Override
    public void readNoticeNumRequest(Integer userId, Integer partyMemberId, Integer noticeInfoId) {
        mRxManage.add(mModel.readNoticeNum(userId,partyMemberId,noticeInfoId)
                .subscribe(new RxSubscriber<NotingResponse>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(null);
                    }
                    @Override
                    protected void _onNext(NotingResponse notingResponse) {
                        mView.returnReadNoticeNum(notingResponse,null);
                        mView.showLoading(null);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,null);
                    }
                }));
    }
}
