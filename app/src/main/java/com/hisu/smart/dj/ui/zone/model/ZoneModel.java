package com.hisu.smart.dj.ui.zone.model;


import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CommentEntity;
import com.hisu.smart.dj.entity.Result;
import com.hisu.smart.dj.entity.StudyListEntity;
import com.hisu.smart.dj.entity.ThumbsUpEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.zone.contract.CircleZoneContract;
import com.jaydenxiao.common.baseapp.AppCache;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;
import com.jaydenxiao.common.commonutils.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * des:
 * Created by xsf
 * on 2016.08.15:56
 */
public class ZoneModel implements CircleZoneContract.Model {
    /**
     * 获取未读条数
     * @return
     */
    @Override
    public Observable<String> getZoneNotReadNews() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(AppCache.getInstance().getIcon());
                subscriber.onCompleted();
                LogUtils.logd(AppCache.getInstance().getIcon());
            }
        }).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 获取党员圈列表
     * @param partyMemberId
     * @param cateId
     * @param cateCode
     * @param id
     * @param publishTime
     * @param pageSize
     * @return
     */
    @Override
    public Observable<StudyListEntity> getListDatas(Integer partyMemberId, Integer cateId, String cateCode, Integer id, String publishTime, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listMemberActionWithPulled(partyMemberId, cateId, cateCode,id,  publishTime,pageSize)
                .map(new Func1<StudyListEntity, StudyListEntity>() {
                    @Override
                    public StudyListEntity call(StudyListEntity studyListEntity) {
                        return studyListEntity;
                    }
                }).compose(RxSchedulers.<StudyListEntity>io_main());
    }

    @Override
    public Observable<BaseResponse<ThumbsUpEntity>> listTheThumbsUp(Integer resId, Integer userId, Integer partyMemberId) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listTheThumbsUp(resId,userId,partyMemberId)
                .map(new Func1<BaseResponse<ThumbsUpEntity>, BaseResponse<ThumbsUpEntity>>() {
                    @Override
                    public BaseResponse<ThumbsUpEntity> call(BaseResponse<ThumbsUpEntity> baseResponse) {
                        return baseResponse;
                    }
                }).compose(RxSchedulers.<BaseResponse<ThumbsUpEntity>>io_main());
    }

    @Override
    public Observable<BaseResponse<CommentEntity>> listTheComment(Integer resId, Integer userId, Integer partyMemberId) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listTheComment(resId,userId,partyMemberId)
                .map(new Func1<BaseResponse<CommentEntity>, BaseResponse<CommentEntity>>() {
                    @Override
                    public BaseResponse<CommentEntity> call(BaseResponse<CommentEntity> baseResponse) {
                        return baseResponse;
                    }
                }).compose(RxSchedulers.<BaseResponse<CommentEntity>>io_main());
    }

    @Override
    public Observable<VisitNumResponse> addComment(Integer resId, Integer userId, Integer partyMemberId, String comment) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .giveTheComment(resId,userId,partyMemberId,comment)
                .map(new Func1<VisitNumResponse, VisitNumResponse>() {
                    @Override
                    public VisitNumResponse call(VisitNumResponse visitNumResponse) {
                        return visitNumResponse;
                    }
                }).compose(RxSchedulers.<VisitNumResponse>io_main());
    }

    @Override
    public Observable<VisitNumResponse> deleteComment(Integer id) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .deleteTheComment(id)
                .map(new Func1<VisitNumResponse, VisitNumResponse>() {
                    @Override
                    public VisitNumResponse call(VisitNumResponse visitNumResponse) {
                        return visitNumResponse;
                    }
                }).compose(RxSchedulers.<VisitNumResponse>io_main());
    }


    /**
     *
     * @param resId
     * @param userId
     * @param partyMemberId
     * @return
     */
    @Override
    public Observable<VisitNumResponse> deleteCircle(Integer resId, Integer userId, Integer partyMemberId) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .deleteMyAction(resId,userId,partyMemberId)
                .map(new Func1<VisitNumResponse, VisitNumResponse>() {
                    @Override
                    public VisitNumResponse call(VisitNumResponse visitNumResponse) {
                        return visitNumResponse;
                    }
                }).compose(RxSchedulers.<VisitNumResponse>io_main());
    }

    /**
     * 点赞
     * @param resId
     * @param userId
     * @param partyMemberId
     * @return
     */
    @Override
    public Observable<VisitNumResponse> addFavort(Integer resId, Integer userId, Integer partyMemberId) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .giveTheThumbsUp(resId,userId,partyMemberId)
                .map(new Func1<VisitNumResponse, VisitNumResponse>() {
                    @Override
                    public VisitNumResponse call(VisitNumResponse visitNumResponse) {
                        return visitNumResponse;
                    }
                }).compose(RxSchedulers.<VisitNumResponse>io_main());
    }

    /**
     * 取消点赞
     * @param id
     * @return
     */
    @Override
    public Observable<VisitNumResponse> deleteFavort(Integer id) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .cancelTheThumbsUp(id)
                .map(new Func1<VisitNumResponse, VisitNumResponse>() {
                    @Override
                    public VisitNumResponse call(VisitNumResponse visitNumResponse) {
                        return visitNumResponse;
                    }
                }).compose(RxSchedulers.<VisitNumResponse>io_main());
    }

}
