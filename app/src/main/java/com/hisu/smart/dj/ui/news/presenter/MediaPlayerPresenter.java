package com.hisu.smart.dj.ui.news.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.StudiedDetailEntity;
import com.hisu.smart.dj.entity.StudiedDetailResponse;
import com.hisu.smart.dj.entity.StudyLogParam;
import com.hisu.smart.dj.ui.news.contract.MediaPlayerContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

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
        mRxManage.add(mModel.addPartyBranchStudyLogs(userId,logId,partyBranchId,resType,resId,resName,duration,studiedHours,resTotalHours, pagePath,remark).subscribe(new RxSubscriber<BaseResponse>(mContext,false) {
            @Override
            protected void _onNext(BaseResponse response) {
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
                                               Integer partyBranchId,
                                               Integer resType,
                                               Integer resId,
                                               String resName,
                                               Long duration,
                                               Float studiedHours,
                                               Float resTotalHours,
                                               String pagePath,
                                               String remark) {
        mRxManage.add(mModel.addPartyMemberStudyLogs(userId,logId,partyBranchId,resType,resId,resName,duration,studiedHours,resTotalHours, pagePath,remark).subscribe(new RxSubscriber<BaseResponse>(mContext,false) {
            @Override
            protected void _onNext(BaseResponse response) {
                mView.returnAddPartyMemberStudyLogs( response);
            }
            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }
}
