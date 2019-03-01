package com.hisu.smart.dj.ui.study.contract;

import com.hisu.smart.dj.entity.StudyListEntity;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import retrofit2.http.Query;
import rx.Observable;

public interface StudyListContract {
    interface Model extends BaseModel {
        //学习心得列表获取
        Observable <StudyListEntity> getListMemberActionWithPulled(Integer partyMemberId,
                                                                   Integer cateId,
                                                                   String cateCode,
                                                                   Integer id,
                                                                   String publishTime,
                                                                   Integer pageSize);
    }

    interface View extends BaseView {
        //学习心得列表返回
        void returnMemberActionWithPulled(StudyListEntity studyListEntity);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取党员学习计划请求
        public abstract void getListMemberActionWithPulledRequest(Integer partyMemberId,
                                                      Integer cateId,
                                                      String cateCode,
                                                      Integer id,
                                                      String publishTime,
                                                      Integer pageSize);
    }
}
