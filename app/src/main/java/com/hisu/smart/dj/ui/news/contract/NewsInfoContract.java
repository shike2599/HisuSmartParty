package com.hisu.smart.dj.ui.news.contract;

import com.hisu.smart.dj.entity.NewsInfoResponse;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

public interface NewsInfoContract {
    interface Model extends BaseModel {
        //请求获取新闻详情
        Observable <NewsInfoResponse> getNewsInfoData(Integer id);
        //践行详情
        Observable <NewsInfoResponse> getFollowInfoData(Integer id);
        //三会一课详情
        Observable <NewsInfoResponse> getTopicInfoData(Integer id);
        //常规学习详情
        Observable <NewsInfoResponse> getCommonInfoData(Integer id);
        //添加收藏
        Observable <UserCollectionEntity> addCollectionData(Integer id, Integer partyBranchId,
                                                      Integer resType, Integer resId);
        //取消收藏
        Observable <NotingResponse> cancelCollection(Integer id);
        //查询收藏状态
        Observable <UserCollectionEntity> getUserCollectionData(Integer id, Integer partyBranchId,
                                                      Integer resType, Integer resId);
    }

    interface View extends BaseView {
        //返回请求获取新闻详情
        void returnNewsInfoData(NewsInfoResponse newsInfoResponse);
        //添加收藏/状态查询
        void returnCollectionData(UserCollectionEntity userCollectionEntity,String tag);
        //取消收藏
        void returnCancelCollectionData(NotingResponse notingResponse);

    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取请求获取新闻详情
        public abstract void getNewsInfoDataRequest(Integer id);
        //践行详情
        public abstract void getFollowInfoDataRequest(Integer id);
        //三会一课详情
        public abstract void getTopicInfoDataRequest(Integer id);
        //常规学习详情
        public abstract void getCommonInfoDataRequest(Integer id);
        //添加收藏
        public abstract void addCollectionDataRequest(Integer id,Integer partyBranchId,
                                                      Integer resType,Integer resId);
        //取消收藏
        public abstract void cancelCollectionRequest(Integer id);
        //查询收藏状态
        public abstract void getUserCollectionDataRequest(Integer id,Integer partyBranchId,
                                                      Integer resType,Integer resId);

    }
}
