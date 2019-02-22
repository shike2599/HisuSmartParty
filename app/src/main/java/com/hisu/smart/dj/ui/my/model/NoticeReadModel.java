package com.hisu.smart.dj.ui.my.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.my.contract.NoticeContract;
import com.hisu.smart.dj.ui.my.contract.NoticeReadContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lichee on 2019/1/28.
 */

public class NoticeReadModel implements NoticeReadContract.Model {
    @Override
    public Observable<NotingResponse> readNoticeNum(Integer userId, Integer partyMemberId, Integer noticeInfoId) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .readNoticeNum(userId,partyMemberId,noticeInfoId)
                .map(new Func1<NotingResponse, NotingResponse>() {
                    @Override
                    public NotingResponse call(NotingResponse notingResponse) {
                        return notingResponse;
                    }
                })
                .compose(RxSchedulers.<NotingResponse>io_main());
    }
}
