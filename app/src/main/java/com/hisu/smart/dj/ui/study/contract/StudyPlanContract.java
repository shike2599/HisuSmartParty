package com.hisu.smart.dj.ui.study.contract;

import com.hisu.smart.dj.entity.RankEntity;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.util.List;

import rx.Observable;

public interface StudyPlanContract {
    interface Model extends BaseModel {
        //请求获取党员学习计划
        Observable <StudyPlanRespone> getMemberPlanData(Integer userId, Integer  partyMemberId, Integer timeType);
        //请求获取支部学习计划
        Observable <StudyPlanRespone> getBranchPlanData(Integer userId, Integer  partyBranchId, Integer timeType);
    }

    interface View extends BaseView {
        //返回党员学习计划
        void returnMemberPlanData(StudyPlanRespone studyPlanRespone, Integer timeType);
        //返回支部学习计划
        void returnBranchPlanData(StudyPlanRespone StudyPlanRespone,Integer timeType);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取党员学习计划请求
        public abstract void getMemberPlanDataRequest(Integer userId, Integer  partyMemberId, Integer timeType);
        //发起获取支部学习计划请求
        public abstract void getBranchPlanDataRequest(Integer userId, Integer  partyBranchId, Integer timeType);
    }
}
