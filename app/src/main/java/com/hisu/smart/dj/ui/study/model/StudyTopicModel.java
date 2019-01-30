package com.hisu.smart.dj.ui.study.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.ui.study.contract.StudyTopicContract;
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
    public Observable<InformationResponse<InformationEntity>> listMemberTopicResPlan(Integer userId, Integer pageNo, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listMemberTopicResPlan(userId,pageNo,pageSize)
                .map(new Func1<InformationResponse<InformationEntity>,InformationResponse<InformationEntity>>() {
                    @Override
                    public InformationResponse<InformationEntity> call(InformationResponse<InformationEntity> topicPlanEntityResponse) {
                        List<InformationEntity> topicPlanEntityList = new ArrayList<>();
                        if(topicPlanEntityResponse != null){
                            int size = topicPlanEntityResponse.getDataList().size();
                            for(int i = 0; i < size; i++){
                                InformationEntity entity = topicPlanEntityResponse.getDataList().get(i);
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
                .compose(RxSchedulers.<InformationResponse<InformationEntity>>io_main());
    }

    @Override
    public Observable<InformationResponse<InformationEntity>> listBranchTopicResPlan(Integer userId, Integer pageNo, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listBranchTopicResPlan(userId,pageNo,pageSize)
                .map(new Func1<InformationResponse<InformationEntity>,InformationResponse<InformationEntity>>() {
                    @Override
                    public InformationResponse<InformationEntity> call(InformationResponse<InformationEntity> topicPlanEntityResponse) {
                        List<InformationEntity> topicPlanEntityList = new ArrayList<>();
                        if(topicPlanEntityResponse != null){
                            int size = topicPlanEntityResponse.getDataList().size();
                            for(int i = 0; i < size; i++){
                                InformationEntity entity = topicPlanEntityResponse.getDataList().get(i);
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
                .compose(RxSchedulers.<InformationResponse<InformationEntity>>io_main());
    }
}
