package com.hisu.smart.dj.ui.main.contract;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.UnReadSizeEntity;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 * des:新闻列表contract
 * Created by xsf
 * on 2016.09.14:38
 */
public interface HomeInfoContract {
    interface Model extends BaseModel {
        //请求通知数据
        Observable <NoticeInfoEntity> getListNoticeByTime(Integer userId, Integer  partyBranchId, String  publishTime, Integer limitNum);
        //未读通知消息个数
        Observable <UnReadSizeEntity> getUnReadNoticeNum(Integer userId, Integer  partyBranchId);
        //请求获取新闻
        Observable <InformationResponse<InformationEntity>> getNewsListData(String cateCode,String keywords,Integer pageNo,Integer pageSize);
        ////专题内容
        Observable <InformationResponse<InformationEntity>> getTopicContentData(String cateCode,String keywords,Integer pageNo,Integer pageSize);
        //践行内容列表
        Observable <InformationResponse<InformationEntity>> getListActionContentData(String cateCode,String keywords,Integer pageNo,Integer pageSize);
    }

    interface View extends BaseView {
        //返回通知数据
        void returnListNoticeByTime(NoticeInfoEntity noticeInfoEntity, String tag);
        //返回未读通知消息个数
        void returnUnReadNoticeNum(UnReadSizeEntity unReadSizeEntity, String tag);
        //返回获取的新闻
        void returnNewsListData(InformationResponse informationResponse,String tag);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起通知数据请求
        public abstract void getListNoticeByTimeRequest(Integer userId, Integer  partyBranchId, String  publishTime, Integer limitNum);
        //未读通知消息个数
        public abstract void getUnReadNoticeNuRequest(Integer userId, Integer  partyBranchId);
        //发起获取新闻请求
        public abstract void getNewsListDataRequest(String cateCode,String keywords,Integer pageNo,Integer pageSize);
        //专题内容
        public abstract void getTopicListContentRequest(String cateCode,String keywords,Integer pageNo,Integer pageSize);
        //践行内容列表
        public abstract void getListActionContentRequest(String cateCode,String keywords,Integer pageNo,Integer pageSize);
    }

}
