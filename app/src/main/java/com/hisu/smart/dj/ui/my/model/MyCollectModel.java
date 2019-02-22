package com.hisu.smart.dj.ui.my.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CollectEntity;
import com.hisu.smart.dj.ui.my.contract.MyCollectContract;
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
 * Created by lichee on 2019/2/21.
 */

public class MyCollectModel implements MyCollectContract.Model {
    @Override
    public Observable<BaseResponse<CollectEntity>> getCollectListData(Integer userId, Integer id, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listMyCollection(userId,id,pageSize)
                .map(new Func1<BaseResponse<CollectEntity>, BaseResponse<CollectEntity>>() {
                    @Override
                    public BaseResponse<CollectEntity> call(BaseResponse<CollectEntity> collects) {
                          List<CollectEntity> collectsList = new ArrayList<>();
                          if(collects != null){
                              if(collects.getDataList() != null){
                                  int size = collects.getDataList().size();
                                  for(int i = 0; i < size; i++){
                                      CollectEntity entity = collects.getDataList().get(i);
                                      Date date = null;
                                      String dateStr;
                                      try {
                                          date = new SimpleDateFormat("yyyy-MM-d H:m:s").parse(entity.getCreateTime());
                                          dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                                          entity.setCreateTime(dateStr);
                                      } catch (ParseException e) {
                                          e.printStackTrace();
                                          entity.setCreateTime("");
                                      }
                                      collectsList.add(entity);
                                  }
                                  collects.getDataList().clear();
                                  collects.getDataList().addAll(collectsList);
                              }
                          }
                          return collects;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<BaseResponse<CollectEntity>>io_main());
    }
}
