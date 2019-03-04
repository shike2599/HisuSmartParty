package com.hisu.smart.dj.ui.news.contract;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
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
 * @date 2019/1/30
 */

public interface PartyNewsContract {
    interface Model extends BaseModel {
        Observable<InformationResponse<InformationEntity>> listInformation(Integer cateId, String cateCode, String codeKeywords, Integer pageNo, Integer pageSize);
        //活动浏览次数统计
        Observable<BaseResponse> addResVisitNum(Integer resType, Integer resId);
        //查询单个活动浏览次数
        Observable<VisitNumResponse> getResVisitNum(Integer resType, Integer resId);
        //查询多个活动浏览次数
        Observable<BaseResponse<VisitNumEntity>> getAllResVisitNum(Integer resType, String resIds);
    }

    interface View extends BaseView {
        void returnlistInformation(InformationResponse<InformationEntity> informationResponse);
        //返回单个活动浏览次数
        void returnResVisitNum(VisitNumResponse visitNumResponse);
        //返回活动浏览次数统计数据
        void returnAddResVisitNum(BaseResponse response);
        //返回多个活动浏览次数数据
        void returnAllResVisitNum(BaseResponse<VisitNumEntity> baseResponse,String code);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void listInformationRequest(Integer cateId, String cateCode, String codeKeywords,Integer pageNo,Integer pageSize);
        //请求返回单个活动浏览次数
        public abstract void getResVisitNumRequest(Integer resType, Integer resId);
        //请求活动浏览次数统计数据
        public abstract void getAddResVisitNumRequest(Integer resType, Integer resId);
        //请求多个活动浏览次数数据
        public abstract void getAllResVisitNumRequest(final String code,Integer resType,  String resIds);
    }
}
