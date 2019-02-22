package com.hisu.smart.dj.ui.my.contract;

import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.NotingResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 * Created by lichee on 2019/1/28.
 */

public interface NoticeReadContract {
    //标记通知公告为已读状态
    interface Model extends BaseModel {
        Observable <NotingResponse> readNoticeNum(Integer userId, Integer partyMemberId, Integer noticeInfoId);
    }

    interface View extends BaseView {
        void returnReadNoticeNum(NotingResponse notingResponse, String tag);
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void readNoticeNumRequest(Integer userId, Integer partyMemberId, Integer noticeInfoId);
    }
}
