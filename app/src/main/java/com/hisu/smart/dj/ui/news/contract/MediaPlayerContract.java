package com.hisu.smart.dj.ui.news.contract;

import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.StudiedDetailEntity;
import com.hisu.smart.dj.entity.StudiedDetailResponse;

import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
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

        Observable<VisitNumResponse> addPartyBranchStudyLogs( Integer userId,
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

        Observable<VisitNumResponse> addPartyMemberStudyLogs( Integer userId,
                                                          Integer logId,
                                                          Integer partyMemberId,
                                                          Integer resType,
                                                          Integer resId,
                                                          String resName,
                                                          Long duration,
                                                          Float studiedHours,
                                                          Float resTotalHours,
                                                          String pagePath,
                                                          String remark);
        //添加收藏
        Observable <UserCollectionEntity> addCollectionData(Integer id, Integer partyBranchId,
                                                            Integer resType, Integer resId);
        //取消收藏
        Observable <NotingResponse> cancelCollection(Integer id);
        //查询收藏状态
        Observable <UserCollectionEntity> getUserCollectionData(Integer id, Integer partyBranchId,
                                                                Integer resType, Integer resId);
    }

    interface View extends BaseView {
        void returnBranchResStudiedDetail(StudiedDetailEntity studiedDetailEntity);
        void returnMemberResStudiedDetail(StudiedDetailEntity studiedDetailEntity);
        void returnAddPartyBranchStudyLogs(VisitNumResponse baseResponse);
        void returnAddPartyMemberStudyLogs(VisitNumResponse baseResponse);

        //添加收藏/状态查询
        void returnCollectionData(UserCollectionEntity userCollectionEntity,String tag);
        //取消收藏
        void returnCancelCollectionData(NotingResponse notingResponse);
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
                                                            Integer partyMemberId,
                                                            Integer resType,
                                                            Integer resId,
                                                            String resName,
                                                            Long duration,
                                                            Float studiedHours,
                                                            Float resTotalHours,
                                                            String pagePath,
                                                            String remark);
        //添加收藏
        public abstract void addCollectionDataRequest(Integer id,Integer partyBranchId,
                                                      Integer resType,Integer resId);
        //取消收藏
        public abstract void cancelCollectionRequest(Integer id);
        //查询收藏状态
        public abstract void getUserCollectionDataRequest(Integer id,Integer partyBranchId,
                                                          Integer resType,Integer resId);
    }
}
