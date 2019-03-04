package com.hisu.smart.dj.ui.main.contract;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.util.List;

import rx.Observable;

/**
 * des:新闻列表contract
 * Created by xsf
 * on 2016.09.14:38
 */
public interface NewsListContract {
    interface Model extends BaseModel {
        //请求获取新闻
        Observable<InformationResponse<InformationEntity>> getNewsListData(String cateCode, String keywords, Integer pageNo, Integer pageSize);

        ////专题内容
        Observable<InformationResponse<InformationEntity>> getTopicContentData(String cateCode, String keywords, Integer pageNo, Integer pageSize);

        //践行内容列表
        Observable<InformationResponse<InformationEntity>> getListActionContentData(String cateCode, String keywords, Integer pageNo, Integer pageSize);

        //活动浏览次数统计
        Observable<BaseResponse> addResVisitNum(Integer resType, Integer resId);

        //查询单个活动浏览次数
        Observable<VisitNumResponse> getResVisitNum(Integer resType, Integer resId);

        //查询多个活动浏览次数
        Observable<BaseResponse<VisitNumEntity>> getAllResVisitNum(Integer resType, String resIds);
    }

    interface View extends BaseView {
        //返回获取的新闻
        void returnNewsListData(InformationResponse informationResponse, String tag);
        //返回单个活动浏览次数
        void returnResVisitNum(VisitNumResponse visitNumResponse);
        //返回活动浏览次数统计数据
        void returnAddResVisitNum(BaseResponse response);
        //返回多个活动浏览次数数据
        void returnAllResVisitNum(BaseResponse<VisitNumEntity> baseResponse, String tag);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取新闻请求
        public abstract void getNewsListDataRequest(String cateCode, String keywords, Integer pageNo, Integer pageSize);
        //专题内容
        public abstract void getTopicListContentRequest(String cateCode, String keywords, Integer pageNo, Integer pageSize);
        //践行内容列表
        public abstract void getListActionContentRequest(String cateCode, String keywords, Integer pageNo, Integer pageSize);
        //请求返回单个活动浏览次数
        public abstract void getResVisitNumRequest(Integer resType, Integer resId);
        //请求活动浏览次数统计数据
        public abstract void getAddResVisitNumRequest(Integer resType, Integer resId);
        //请求多个活动浏览次数数据
        public abstract void getAllResVisitNumRequest(String cateCode,Integer resType,  String resIds);
    }

}
