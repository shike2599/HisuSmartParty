package com.hisu.smart.dj.ui.study.contract;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 *
 * @author lichee
 * @date 2019/1/29
 */

public interface StudyTopicContract {
    interface Model extends BaseModel {
        Observable<InformationResponse<InformationEntity>> listMemberTopicResPlan( Integer userId, Integer pageNo, Integer pageSize);
        Observable<InformationResponse<InformationEntity>> listBranchTopicResPlan( Integer userId, Integer pageNo, Integer pageSize);
    }

    interface View extends BaseView {
        void returnMemberTopicData(InformationResponse<InformationEntity> informationResponse);
        void returnBranchTopicData(InformationResponse<InformationEntity> informationResponse);
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void getMemberTopicDataRequest(Integer userId, Integer pageNo, Integer pageSize);
        public abstract void getBranchTopicDataRequest(Integer userId, Integer pageNo, Integer pageSize);
    }
}
