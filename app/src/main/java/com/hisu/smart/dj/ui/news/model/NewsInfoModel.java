package com.hisu.smart.dj.ui.news.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.NewsInfoResponse;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.ui.news.contract.NewsInfoContract;
import com.hisu.smart.dj.ui.study.contract.StudyPlanContract;
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
}
