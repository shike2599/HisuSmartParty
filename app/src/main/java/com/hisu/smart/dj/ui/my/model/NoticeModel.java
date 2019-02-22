package com.hisu.smart.dj.ui.my.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.ui.my.contract.NoticeContract;
import com.hisu.smart.dj.ui.my.contract.ResetPasswordContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lichee on 2019/1/28.
 */

public class NoticeModel implements NoticeContract.Model {
    @Override
    public Observable<NoticeInfoEntity> getListNoticeByTime(Integer userId, Integer partyBranchId, Integer id,String publishTime, Integer limitNum) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .listNoticeByTime(userId,partyBranchId,id,publishTime,limitNum)
                .map(new Func1<NoticeInfoEntity, NoticeInfoEntity>() {
                    @Override
                    public NoticeInfoEntity call(NoticeInfoEntity noticeInfoEntity) {
                        return noticeInfoEntity;
                    }
                })
                .compose(RxSchedulers.<NoticeInfoEntity>io_main());
    }
}
