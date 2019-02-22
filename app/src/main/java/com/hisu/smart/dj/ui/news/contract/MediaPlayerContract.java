package com.hisu.smart.dj.ui.news.contract;

import com.hisu.smart.dj.entity.StudiedDetailEntity;
import com.hisu.smart.dj.entity.StudiedDetailResponse;
import com.hisu.smart.dj.entity.StudyLogParam;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import rx.Observable;

/**
 * Created by lichee on 2019/2/22.
 */

public interface MediaPlayerContract {
    interface Model extends BaseModel {
        Observable<StudiedDetailResponse> getBranchResStudiedDetail(Integer userId, Integer resType, Integer resId);
        Observable<StudiedDetailResponse>getMemberResStudiedDetail(Integer userId,Integer resType,Integer resId);

        Observable<BaseResponse> addPartyBranchStudyLogs( Integer userId,
                                                          Integer logId,
                                                          Integer partyBranchId,
                                                          Integer resType,
                                                          Integer resId,
                                                          String resName,
                                                          Long duration,
                                                          Float studiedHours,
                                                          Float resTotalHours,
                                                          String pagePath,
                                                          String remark);

        Observable<BaseResponse> addPartyMemberStudyLogs( Integer userId,
                                                          Integer logId,
                                                          Integer partyBranchId,
                                                          Integer resType,
                                                          Integer resId,
                                                          String resName,
                                                          Long duration,
                                                          Float studiedHours,
                                                          Float resTotalHours,
                                                          String pagePath,
                                                          String remark);
    }

    interface View extends BaseView {
        void returnBranchResStudiedDetail(StudiedDetailEntity studiedDetailEntity);
        void returnMemberResStudiedDetail(StudiedDetailEntity studiedDetailEntity);
        void returnAddPartyBranchStudyLogs(BaseResponse baseResponse);
        void returnAddPartyMemberStudyLogs(BaseResponse baseResponse);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getBranchResStudiedDetailRequest(Integer userId,Integer resType,Integer resId);
        public abstract void getMemberResStudiedDetailRequest(Integer userId,Integer resType,Integer resId);
        public abstract void addPartyBranchStudyLogsRequest(Integer userId,
                                                            Integer logId,
                                                            Integer partyBranchId,
                                                            Integer resType,
                                                            Integer resId,
                                                            String resName,
                                                            Long duration,
                                                            Float studiedHours,
                                                            Float resTotalHours,
                                                            String pagePath,
                                                            String remark);
        public abstract void addPartyMemberStudyLogsRequest(Integer userId,
                                                            Integer logId,
                                                            Integer partyBranchId,
                                                            Integer resType,
                                                            Integer resId,
                                                            String resName,
                                                            Long duration,
                                                            Float studiedHours,
                                                            Float resTotalHours,
                                                            String pagePath,
                                                            String remark);
    }
}
