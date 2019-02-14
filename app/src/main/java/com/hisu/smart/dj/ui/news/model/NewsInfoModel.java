package com.hisu.smart.dj.ui.news.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.NewsInfoResponse;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.news.contract.NewsInfoContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;


/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 * @author lichee
 */
public class NewsInfoModel implements NewsInfoContract.Model {
    //资讯类
    @Override
    public Observable<NewsInfoResponse> getNewsInfoData(Integer id) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .getInformation(id)
                .map(new Func1<NewsInfoResponse, NewsInfoResponse>() {
                    @Override
                    public NewsInfoResponse call(NewsInfoResponse newsInfoResponse) {
                        return newsInfoResponse;
                    }
                }).compose(RxSchedulers.<NewsInfoResponse>io_main());
    }
    //践行详情
    @Override
    public Observable<NewsInfoResponse> getFollowInfoData(Integer id) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .getActionContent(id)
                .map(new Func1<NewsInfoResponse, NewsInfoResponse>() {
                    @Override
                    public NewsInfoResponse call(NewsInfoResponse newsInfoResponse) {
                        return newsInfoResponse;
                    }
                }).compose(RxSchedulers.<NewsInfoResponse>io_main());
    }
    //专题详情
    @Override
    public Observable<NewsInfoResponse> getTopicInfoData(Integer id) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .getTopicContent(id)
                .map(new Func1<NewsInfoResponse, NewsInfoResponse>() {
                    @Override
                    public NewsInfoResponse call(NewsInfoResponse newsInfoResponse) {
                        return newsInfoResponse;
                    }
                }).compose(RxSchedulers.<NewsInfoResponse>io_main());
    }

    //常规详情
    @Override
    public Observable<NewsInfoResponse> getCommonInfoData(Integer id) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .getCommonContent(id)
                .map(new Func1<NewsInfoResponse, NewsInfoResponse>() {
                    @Override
                    public NewsInfoResponse call(NewsInfoResponse newsInfoResponse) {
                        return newsInfoResponse;
                    }
                }).compose(RxSchedulers.<NewsInfoResponse>io_main());
    }
    //添加收藏
    @Override
    public Observable<NotingResponse> addCollectionData(Integer id, Integer partyBranchId, Integer resType, Integer resId) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .addCollection(id,partyBranchId,resType,resId)
                .map(new Func1<NotingResponse, NotingResponse>() {
                    @Override
                    public NotingResponse call(NotingResponse notingResponse) {
                        return notingResponse;
                    }
                })
                .compose(RxSchedulers.<NotingResponse>io_main());
    }
}
