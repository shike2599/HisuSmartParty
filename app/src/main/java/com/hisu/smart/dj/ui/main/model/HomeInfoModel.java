package com.hisu.smart.dj.ui.main.model;

import android.util.Log;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.UnReadSizeEntity;
import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.ui.main.contract.HomeInfoContract;
import com.hisu.smart.dj.ui.main.contract.NewsListContract;
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
 */
public class HomeInfoModel implements HomeInfoContract.Model {

    @Override
    public Observable<NoticeInfoEntity> getListNoticeByTime(Integer userId, Integer partyBranchId,Integer id, String publishTime, Integer limitNum) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .listNoticeByTime(userId,partyBranchId,id,publishTime,limitNum)
                .map(new Func1<NoticeInfoEntity, NoticeInfoEntity>() {
                    @Override
                    public NoticeInfoEntity call(NoticeInfoEntity noticeInfoEntity) {
                        return noticeInfoEntity;
                    }
                })
                .compose(RxSchedulers.<NoticeInfoEntity>io_main());
    }

    @Override
    public Observable<UserCollectionEntity> getUnReadNoticeNum(Integer userId, Integer partyBranchId) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .getUnReadNoticeNum(userId,partyBranchId)
                .map(new Func1<UserCollectionEntity, UserCollectionEntity>() {
                    @Override
                    public UserCollectionEntity call(UserCollectionEntity userCollectionEntity) {
                        return userCollectionEntity;
                    }
                })
                .compose(RxSchedulers.<UserCollectionEntity>io_main());
    }

    @Override
    public Observable<InformationResponse<InformationEntity>> getNewsListData(String cateCode,String keywords,Integer pageNo,Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listInformation(cateCode,keywords,pageNo,pageSize)
                .map(new Func1<InformationResponse<InformationEntity>, InformationResponse<InformationEntity>>() {
                    @Override
                    public InformationResponse<InformationEntity> call(InformationResponse<InformationEntity> informations) {
//                          List<InformationEntity> informationEntityList = new ArrayList<>();
//                          if(informations != null){
//                              int size = informations.getDataList().size();
//                              for(int i = 0; i < size; i++){
//                                  InformationEntity entity = informations.getDataList().get(i);
//                                  entity.setIcon(informations.getOutServer()+entity.getIcon());
//                                  Date date = null;
//                                  String dateStr;
//                                  try {
//                                      date = new SimpleDateFormat("yyyy-MM-d H:m:s").parse(entity.getPublishTime());
//                                      dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(date);
//                                     entity.setPublishTime(dateStr);
//                                  } catch (ParseException e) {
//                                      e.printStackTrace();
//                                      entity.setPublishTime("");
//                                  }
//                                  informationEntityList.add(entity);
//                              }
//                              informations.getDataList().clear();
//                              informations.getDataList().addAll(informationEntityList);
//                          }
//                          return informations;
                        return resetInfomation(informations);
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<InformationResponse<InformationEntity>>io_main());
    }
    //专题列表（三会一课）
    @Override
    public Observable<InformationResponse<InformationEntity>> getTopicContentData(String cateCode, String keywords, Integer pageNo, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .listTopicContent(cateCode,keywords,pageNo,pageSize)
                .map(new Func1<InformationResponse<InformationEntity>, InformationResponse<InformationEntity>>() {
                    @Override
                    public InformationResponse<InformationEntity> call(InformationResponse<InformationEntity> informations) {
                        return resetInfomation(informations);
                    }
                })
                .compose(RxSchedulers.<InformationResponse<InformationEntity>>io_main());
    }
    //践行列表
    @Override
    public Observable<InformationResponse<InformationEntity>> getListActionContentData(String cateCode, String keywords, Integer pageNo, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .listActionContent(cateCode,keywords,pageNo,pageSize)
                .map(new Func1<InformationResponse<InformationEntity>, InformationResponse<InformationEntity>>() {
                    @Override
                    public InformationResponse<InformationEntity> call(InformationResponse<InformationEntity> informations) {
                        return resetInfomation(informations);
                    }
                })
                .compose(RxSchedulers.<InformationResponse<InformationEntity>>io_main());
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


    //重新封装返回的数据
    private InformationResponse<InformationEntity>  resetInfomation(
            InformationResponse<InformationEntity> informations){

        List<InformationEntity> informationEntityList = new ArrayList<>();
        if (informations != null) {
            int size = informations.getDataList().size();
            for (int i = 0; i < size; i++) {
                InformationEntity entity = informations.getDataList().get(i);
                entity.setIcon(informations.getOutServer() + entity.getIcon());
                Log.d("NewsListModel","icon--URL===" + entity.getIcon());
                Date date = null;
                String dateStr;
                try {
                    date = new SimpleDateFormat("yyyy-MM-d H:m:s").parse(entity.getPublishTime());
                    dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                    entity.setPublishTime(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    entity.setPublishTime("");
                }
                informationEntityList.add(entity);
            }
            informations.getDataList().clear();
            informations.getDataList().addAll(informationEntityList);
        }
        return informations;
    }
}
