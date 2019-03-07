package com.hisu.smart.dj.ui.news.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;

import com.hisu.smart.dj.entity.NotingResponse;

import com.hisu.smart.dj.entity.StudiedDetailResponse;

import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.news.contract.MediaPlayerContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lichee on 2019/2/22.
 */

public class MediaPlayerModel implements MediaPlayerContract.Model {
    @Override
    public Observable<StudiedDetailResponse> getBranchResStudiedDetail(Integer userId, Integer resType, Integer resId) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .getBranchResStudiedDetail(userId,resType,resId)
                .map(new Func1<StudiedDetailResponse, StudiedDetailResponse>() {
                    @Override
                    public StudiedDetailResponse call(StudiedDetailResponse response) {
                        return response;
                    }
                }).compose(RxSchedulers.<StudiedDetailResponse>io_main());
    }

    @Override
    public Observable<StudiedDetailResponse> getMemberResStudiedDetail(Integer userId, Integer resType, Integer resId) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .getMemberResStudiedDetail(userId,resType,resId)
                .map(new Func1<StudiedDetailResponse, StudiedDetailResponse>() {
                    @Override
                    public StudiedDetailResponse call(StudiedDetailResponse response) {
                        return response;
                    }
                }).compose(RxSchedulers.<StudiedDetailResponse>io_main());
    }

    @Override
    public Observable<VisitNumResponse> addPartyBranchStudyLogs(Integer userId,
                                                                Integer logId,
                                                                Integer partyBranchId,
                                                                Integer resType,
                                                                Integer resId,
                                                                String resName,
                                                                Long duration,
                                                                Float studiedHours,
                                                                Float resTotalHours,
                                                                String pagePath,
                                                                String remark){
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .addPartyBranchStudyLogs(userId,logId,partyBranchId,resType,resId,resName,duration,studiedHours,resTotalHours, pagePath,remark)
                .map(new Func1<VisitNumResponse, VisitNumResponse>() {
                    @Override
                    public VisitNumResponse call(VisitNumResponse response) {
                        return response;
                    }
                }).compose(RxSchedulers.<VisitNumResponse>io_main());
    }

    @Override
    public Observable<VisitNumResponse> addPartyMemberStudyLogs(Integer userId,
                                                            Integer logId,
                                                            Integer partyMemberId,
                                                            Integer resType,
                                                            Integer resId,
                                                            String resName,
                                                            Long duration,
                                                            Float studiedHours,
                                                            Float resTotalHours,
                                                            String pagePath,
                                                            String remark) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .addPartyMemberStudyLogs(userId,logId,partyMemberId,resType,resId,resName,duration,studiedHours,resTotalHours, pagePath,remark)
                .map(new Func1<VisitNumResponse, VisitNumResponse>() {
                    @Override
                    public VisitNumResponse call(VisitNumResponse response) {
                        return response;
                    }
                }).compose(RxSchedulers.<VisitNumResponse>io_main());
    }

    @Override
    public Observable<UserCollectionEntity> addCollectionData(Integer id, Integer partyBranchId, Integer resType, Integer resId) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .addCollection(id,partyBranchId,resType,resId)
                .map(new Func1<UserCollectionEntity, UserCollectionEntity>() {
                    @Override
                    public UserCollectionEntity call(UserCollectionEntity userCollectionEntity) {
                        return userCollectionEntity;
                    }
                })
                .compose(RxSchedulers.<UserCollectionEntity>io_main());
    }

    @Override
    public Observable<NotingResponse> cancelCollection(Integer id) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .cancelCollection(id)
                .map(new Func1<NotingResponse, NotingResponse>() {
                    @Override
                    public NotingResponse call(NotingResponse notingResponse) {
                        return notingResponse;
                    }
                })
                .compose(RxSchedulers.<NotingResponse>io_main());
    }

    @Override
    public Observable<UserCollectionEntity> getUserCollectionData(Integer id, Integer partyBranchId, Integer resType, Integer resId) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .getUserCollection(id,partyBranchId,resType,resId)
                .map(new Func1<UserCollectionEntity, UserCollectionEntity>() {
                    @Override
                    public UserCollectionEntity call(UserCollectionEntity userCollectionEntity) {
                        return userCollectionEntity;
                    }
                })
                .compose(RxSchedulers.<UserCollectionEntity>io_main());
    }

}
