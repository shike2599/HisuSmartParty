package com.hisu.smart.dj.ui.study.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;

import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.StudyPlanEntity;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.study.contract.StudyTopicContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;


/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 * @author lichee
 */
public class StudyTopicModel implements StudyTopicContract.Model {

    @Override
    public Observable<InformationResponse<StudyPlanEntity>> listMemberTopicResPlan(Integer userId, Integer pageNo, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listMemberTopicResPlan(userId,pageNo,pageSize)
                .map(new Func1<InformationResponse<StudyPlanEntity>,InformationResponse<StudyPlanEntity>>() {
                    @Override
                    public InformationResponse<StudyPlanEntity> call(InformationResponse<StudyPlanEntity> topicPlanEntityResponse) {
                        List<StudyPlanEntity> topicPlanEntityList = new ArrayList<>();
                        if(topicPlanEntityResponse != null){
                            int size = topicPlanEntityResponse.getDataList().size();
                            for(int i = 0; i < size; i++){
                                StudyPlanEntity entity = topicPlanEntityResponse.getDataList().get(i);
                                entity.setIcon(topicPlanEntityResponse.getOutServer()+entity.getIcon());
                                Date date = null;
                                String dateStr;
                                try {
                                    date = new SimpleDateFormat("yyyy-MM-d H:m:s").parse(entity.getPublishTime());
                                    dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                    entity.setPublishTime(dateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    entity.setPublishTime("");
                                }
                                topicPlanEntityList.add(entity);
                            }
                            topicPlanEntityResponse.getDataList().clear();
                            topicPlanEntityResponse.getDataList().addAll(topicPlanEntityList);
                        }
                        return topicPlanEntityResponse;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<InformationResponse<StudyPlanEntity>>io_main());
    }

    @Override
    public Observable<InformationResponse<StudyPlanEntity>> listBranchTopicResPlan(Integer userId, Integer pageNo, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listBranchTopicResPlan(userId,pageNo,pageSize)
                .map(new Func1<InformationResponse<StudyPlanEntity>,InformationResponse<StudyPlanEntity>>() {
                    @Override
                    public InformationResponse<StudyPlanEntity> call(InformationResponse<StudyPlanEntity> topicPlanEntityResponse) {
                        List<StudyPlanEntity> topicPlanEntityList = new ArrayList<>();
                        if(topicPlanEntityResponse != null){
                            int size = topicPlanEntityResponse.getDataList().size();
                            for(int i = 0; i < size; i++){
                                StudyPlanEntity entity = topicPlanEntityResponse.getDataList().get(i);
                                entity.setIcon(topicPlanEntityResponse.getOutServer()+entity.getIcon());
                                Date date = null;
                                String dateStr;
                                try {
                                    date = new SimpleDateFormat("yyyy-MM-d H:m:s").parse(entity.getPublishTime());
                                    dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                    entity.setPublishTime(dateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    entity.setPublishTime("");
                                }
                                topicPlanEntityList.add(entity);
                            }
                            topicPlanEntityResponse.getDataList().clear();
                            topicPlanEntityResponse.getDataList().addAll(topicPlanEntityList);
                        }
                        return topicPlanEntityResponse;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<InformationResponse<StudyPlanEntity>>io_main());
    }

    @Override
    public Observable<BaseResponse> addResVisitNum(Integer resType, Integer resId) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .addResVisitNum(resType,resId)
                .map(new Func1<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse call(BaseResponse baseResponse) {
                        return baseResponse;
                    }
                })
                .compose(RxSchedulers.<BaseResponse>io_main());
    }

    @Override
    public Observable<VisitNumResponse> getResVisitNum(Integer resType, Integer resId) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .getResVisitNum(resType,resId)
                .map(new Func1<VisitNumResponse, VisitNumResponse>() {
                    @Override
                    public VisitNumResponse call(VisitNumResponse visitNumResponse) {
                        return visitNumResponse;
                    }
                })
                .compose(RxSchedulers.<VisitNumResponse>io_main());
    }

    @Override
    public Observable<BaseResponse<VisitNumEntity>> getAllResVisitNum(Integer resType, String resIds) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .getAllResVisitNum(resType,resIds)
                .map(new Func1<BaseResponse<VisitNumEntity>, BaseResponse<VisitNumEntity>>() {
                    @Override
                    public BaseResponse<VisitNumEntity> call(BaseResponse<VisitNumEntity> baseResponse) {
                        return baseResponse;
                    }
                })
                .compose(RxSchedulers.<BaseResponse<VisitNumEntity>>io_main());

    }
}
