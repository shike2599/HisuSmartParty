package com.hisu.smart.dj.ui.study.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.RankEntity;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.ui.study.contract.StudyPlanContract;
import com.hisu.smart.dj.ui.study.contract.StudyRankContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 * @author lichee
 */
public class StudyPlanPresenter extends StudyPlanContract.Presenter {
    //党员个人学习计划
    @Override
    public void getMemberPlanDataRequest(Integer userId, Integer partyMemberId,final Integer timeType) {
       mRxManage.add(mModel.getMemberPlanData(userId,partyMemberId,timeType)
       .subscribe(new RxSubscriber<StudyPlanRespone>(mContext,false) {
           @Override
           public void onStart() {
               super.onStart();
               mView.showLoading(mContext.getString(R.string.loading));
           }

           @Override
           protected void _onNext(StudyPlanRespone studyPlanRespone) {
               mView.returnMemberPlanData(studyPlanRespone,timeType);
               mView.stopLoading(String.valueOf(timeType));
           }

           @Override
           protected void _onError(String message) {
               mView.showErrorTip(message,String.valueOf(timeType));
           }
       }));
    }
    //支部学习计划
    @Override
    public void getBranchPlanDataRequest(Integer userId, Integer partyBranchId,final Integer timeType) {
        mRxManage.add(mModel.getBranchPlanData(userId,partyBranchId,timeType)
                .subscribe(new RxSubscriber<StudyPlanRespone>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(StudyPlanRespone studyPlanRespone) {
                        mView.returnBranchPlanData(studyPlanRespone,timeType);
                        mView.stopLoading(String.valueOf(timeType));
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,String.valueOf(timeType));
                    }
                }));
    }
}
