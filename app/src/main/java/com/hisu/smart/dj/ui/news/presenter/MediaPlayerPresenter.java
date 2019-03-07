package com.hisu.smart.dj.ui.news.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.StudiedDetailEntity;
import com.hisu.smart.dj.entity.StudiedDetailResponse;

import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.news.contract.MediaPlayerContract;

import com.jaydenxiao.common.baserx.RxSubscriber;



/**
 * Created by lichee on 2019/2/22.
 */

public class MediaPlayerPresenter extends MediaPlayerContract.Presenter {
    @Override
    public void getBranchResStudiedDetailRequest(Integer userId, Integer resType, final Integer resId) {
        mRxManage.add(mModel.getBranchResStudiedDetail(userId,resType,resId).subscribe(new RxSubscriber<StudiedDetailResponse>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(StudiedDetailResponse response) {
                StudiedDetailEntity entity = response.getData();
                if(entity != null){
                    mView.returnBranchResStudiedDetail(entity);
                    mView.stopLoading("");
                }else {
                    mView.stopLoading("EMPTY");
                }

            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }

    @Override
    public void getMemberResStudiedDetailRequest(Integer userId, Integer resType, Integer resId) {
        mRxManage.add(mModel.getMemberResStudiedDetail(userId,resType,resId).subscribe(new RxSubscriber<StudiedDetailResponse>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(StudiedDetailResponse response) {
               StudiedDetailEntity entity = response.getData();
                if(entity != null){
                    mView.returnMemberResStudiedDetail(entity);
                    mView.stopLoading("");
                }else {
                    mView.stopLoading("EMPTY");
                }
            }
            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }

    @Override
    public void addPartyBranchStudyLogsRequest(Integer userId,
                                               Integer logId,
                                               Integer partyBranchId,
                                               Integer resType,
                                               Integer resId,
                                               String resName,
                                               Long duration,
                                               Float studiedHours,
                                               Float resTotalHours,
                                               String pagePath,
                                               String remark) {
        mRxManage.add(mModel.addPartyBranchStudyLogs(userId,logId,partyBranchId,resType,resId,resName,duration,studiedHours,resTotalHours, pagePath,remark).subscribe(new RxSubscriber<VisitNumResponse>(mContext,false) {
            @Override
            protected void _onNext(VisitNumResponse response) {
                mView.returnAddPartyBranchStudyLogs(response);
            }
            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }

    @Override
    public void addPartyMemberStudyLogsRequest(Integer userId,
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
        mRxManage.add(mModel.addPartyMemberStudyLogs(userId,logId,partyMemberId,resType,resId,resName,duration,studiedHours,resTotalHours, pagePath,remark).subscribe(new RxSubscriber<VisitNumResponse>(mContext,false) {
            @Override
            protected void _onNext(VisitNumResponse response) {
                mView.returnAddPartyMemberStudyLogs( response);
            }
            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }

    @Override
    public void addCollectionDataRequest(Integer id, Integer partyBranchId, Integer resType, Integer resId) {
        mRxManage.add(mModel.addCollectionData(id,partyBranchId,resType,resId)
                .subscribe(new RxSubscriber<UserCollectionEntity>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(UserCollectionEntity userCollectionEntity) {
                        mView.stopLoading("collection");
                        mView.returnCollectionData(userCollectionEntity, AppConstant.ADD_COLLECTION_TAG);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,"collection");
                    }
                }));
    }

    @Override
    public void cancelCollectionRequest(Integer id) {
        mRxManage.add(mModel.cancelCollection(id)
                .subscribe(new RxSubscriber<NotingResponse>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(NotingResponse notingResponse) {
                        mView.stopLoading("collection");
                        mView.returnCancelCollectionData(notingResponse);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,"collection");
                    }
                }));
    }

    @Override
    public void getUserCollectionDataRequest(Integer id, Integer partyBranchId, Integer resType, Integer resId) {
        mRxManage.add(mModel.getUserCollectionData(id,partyBranchId,resType,resId)
                .subscribe(new RxSubscriber<UserCollectionEntity>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(UserCollectionEntity userCollectionEntity) {
                        mView.stopLoading("UserCollection");
                        mView.returnCollectionData(userCollectionEntity, AppConstant.QUERY_COLLECTION_TAG);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,"UserCollection");
                    }
                }));
    }

}
