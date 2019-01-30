package com.hisu.smart.dj.ui.study.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.RankEntity;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.ui.study.contract.StudyPlanContract;
import com.hisu.smart.dj.ui.study.contract.StudyRankContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;


/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 * @author lichee
 */
public class StudyPlanModel implements StudyPlanContract.Model {

    //党员学习计划
    @Override
    public Observable<StudyPlanRespone> getMemberPlanData(Integer userId, Integer partyMemberId, Integer timeType) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .partyMemberPlan(userId,partyMemberId,timeType)
                .map(new Func1<StudyPlanRespone, StudyPlanRespone>() {
                    @Override
                    public StudyPlanRespone call(StudyPlanRespone studyPlanRespone) {
                        return studyPlanRespone;
                    }
                }) //声明线程调度
                .compose(RxSchedulers.<StudyPlanRespone>io_main());

    }
    //支部学习计划
    @Override
    public Observable<StudyPlanRespone> getBranchPlanData(Integer userId, Integer partyBranchId, Integer timeType) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .partyBranchPlan(userId,partyBranchId,timeType)
                .map(new Func1<StudyPlanRespone, StudyPlanRespone>() {
                    @Override
                    public StudyPlanRespone call(StudyPlanRespone studyPlanRespone) {
                        return studyPlanRespone;
                    }
                })
                .compose(RxSchedulers.<StudyPlanRespone>io_main());
    }
}
