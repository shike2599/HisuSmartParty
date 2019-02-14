package com.hisu.smart.dj.ui.news.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.ui.news.contract.PartyNewsMainContract;
import com.hisu.smart.dj.ui.study.contract.StudyCommonMainContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public class PartyNewsMainModel implements PartyNewsMainContract.Model {
    @Override
    public Observable<BaseResponse<CateEntity>> listInfoCate(Integer parentId, String parentCode, String codeKeywords) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listInfoCate(parentId,parentCode,codeKeywords)
                .map(new Func1<BaseResponse<CateEntity>, BaseResponse<CateEntity>>() {
                    @Override
                    public BaseResponse<CateEntity> call(BaseResponse<CateEntity> cateEntityBaseResponse) {
                        return cateEntityBaseResponse;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<BaseResponse<CateEntity>>io_main());
    }
}
