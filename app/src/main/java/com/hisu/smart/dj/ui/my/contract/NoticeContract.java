package com.hisu.smart.dj.ui.my.contract;

import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import rx.Observable;

/**
 * Created by lichee on 2019/1/28.
 */

public interface NoticeContract {
    interface Model extends BaseModel {
        Observable <NoticeInfoEntity> getListNoticeByTime(Integer userId, Integer  partyBranchId,Integer id, String  publishTime, Integer limitNum);
    }

    interface View extends BaseView {
        void returnListNoticeByTime(NoticeInfoEntity noticeInfoEntity, String tag);
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void getListNoticeByTimeRequest(Integer userId, Integer  partyBranchId,Integer id,String  publishTime, Integer limitNum);
    }
}
