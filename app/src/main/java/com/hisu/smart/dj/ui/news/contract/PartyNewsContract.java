package com.hisu.smart.dj.ui.news.contract;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public interface PartyNewsContract {
    interface Model extends BaseModel {
        Observable<InformationResponse<InformationEntity>> listInformation(Integer cateId, String cateCode, String codeKeywords, Integer pageNo, Integer pageSize);
    }

    interface View extends BaseView {
        void returnlistInformation(InformationResponse<InformationEntity> informationResponse);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void listInformationRequest(Integer cateId, String cateCode, String codeKeywords,Integer pageNo,Integer pageSize);
    }
}
