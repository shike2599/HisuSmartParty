package com.hisu.smart.dj.ui.study.contract;

import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.StudyPlanEntity;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import rx.Observable;

/**
 *
 * @author lichee
 * @date 2019/1/29
 */

public interface StudyTopicContract {
    interface Model extends BaseModel {
        Observable<InformationResponse<StudyPlanEntity>> listMemberTopicResPlan(Integer userId, Integer pageNo, Integer pageSize);
        Observable<InformationResponse<StudyPlanEntity>> listBranchTopicResPlan( Integer userId, Integer pageNo, Integer pageSize);
        //活动浏览次数统计
        Observable<BaseResponse> addResVisitNum(Integer resType, Integer resId);
        //查询单个活动浏览次数
        Observable<VisitNumResponse> getResVisitNum(Integer resType, Integer resId);
        //查询多个活动浏览次数
        Observable<BaseResponse<VisitNumEntity>> getAllResVisitNum(Integer resType, String resIds);

    }

    interface View extends BaseView {
        void returnMemberTopicData(InformationResponse<StudyPlanEntity> informationResponse);
        void returnBranchTopicData(InformationResponse<StudyPlanEntity> informationResponse);
        //返回单个活动浏览次数
        void returnResVisitNum(VisitNumResponse visitNumResponse);
        //返回活动浏览次数统计数据
        void returnAddResVisitNum(BaseResponse response);
        //返回多个活动浏览次数数据
        void returnAllResVisitNum(BaseResponse<VisitNumEntity> baseResponse, String code);
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void getMemberTopicDataRequest(Integer userId, Integer pageNo, Integer pageSize);
        public abstract void getBranchTopicDataRequest(Integer userId, Integer pageNo, Integer pageSize);
        //请求返回单个活动浏览次数
        public abstract void getResVisitNumRequest(Integer resType, Integer resId);
        //请求活动浏览次数统计数据
        public abstract void getAddResVisitNumRequest(Integer resType, Integer resId);
        //请求多个活动浏览次数数据
        public abstract void getAllResVisitNumRequest(final String code,Integer resType,  String resIds);
    }
}
