package com.hisu.smart.dj.ui.study.presenter;

import com.hisu.smart.dj.R;

import com.hisu.smart.dj.entity.RankEntity;
import com.hisu.smart.dj.ui.study.contract.StudyRankContract;

import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 * @author lichee
 */
public class StudyRankPresenter extends StudyRankContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getMemberRankListDataRequest(Integer userId, Integer partyMemberId, Integer sortType, Integer limitNum) {
        mRxManage.add(mModel.getMemberRankListData(userId,partyMemberId,sortType,limitNum).subscribe(new RxSubscriber<BaseResponse<RankEntity>>(mContext,false)  {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseResponse<RankEntity> rankEntityBaseResponse) {
                mView.returnMemberRankListData(rankEntityBaseResponse.getDataList());
                mView.stopLoading(null);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }

    @Override
    public void getBranchRankListDataRequest(Integer userId, Integer partyBranchId, Integer sortType, Integer limitNum) {
        mRxManage.add(mModel.getBranchRankListData(userId,partyBranchId,sortType,limitNum).subscribe(new RxSubscriber<BaseResponse<RankEntity>>(mContext,false)  {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseResponse<RankEntity> rankEntityBaseResponse) {
                mView.returnMemberRankListData(rankEntityBaseResponse.getDataList());
                mView.stopLoading(null);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));

    }
}
