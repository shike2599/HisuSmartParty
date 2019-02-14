package com.hisu.smart.dj.ui.news.contract;

import com.hisu.smart.dj.entity.NewsInfoResponse;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

public interface NewsInfoContract {
    interface Model extends BaseModel {
        //请求获取新闻详情
        Observable <NewsInfoResponse> getNewsInfoData(Integer id);
        //三会一课详情
        Observable <NewsInfoResponse> getFollowInfoData(Integer id);
        //践行详情
        Observable <NewsInfoResponse> getTopicInfoData(Integer id);
        //常规学习详情
        Observable <NewsInfoResponse> getCommonInfoData(Integer id);
    }

    interface View extends BaseView {
        //返回请求获取新闻详情
        void returnNewsInfoData(NewsInfoResponse newsInfoResponse);

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
    }
}
