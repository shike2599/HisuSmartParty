package com.hisu.smart.dj.ui.study.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.ui.study.contract.StudyCommonMainContract;
import com.hisu.smart.dj.ui.study.contract.UpLoadFileContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public class UpLoadFileModel implements UpLoadFileContract.Model {
    @Override
    public Observable<BaseResponse> submitActionContent(Integer userId, Integer partyMemberId, Integer cateId,
                                                        String cateCode, String name, String icon,
                                                        Map<String, RequestBody> imgPaths, Integer mediaType,
                                                        String content, String url, String publishTime, Boolean isNeedSign) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .submitActionContent(userId,partyMemberId,cateId,cateCode,name,icon,imgPaths,
                        mediaType,content,url,publishTime,isNeedSign)
                .map(new Func1<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse call(BaseResponse baseResponse) {
                        return baseResponse;
                    }
                })
                .compose(RxSchedulers.<BaseResponse>io_main());
    }
}
