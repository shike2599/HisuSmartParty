package com.hisu.smart.dj.ui.study.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.UpLoadFileResponse;
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
    public Observable<NotingResponse> submitActionContent(Integer userId, Integer partyMemberId, Integer cateId,
                                                        String cateCode, String name, String icon,
                                                        String imgPaths, Integer mediaType,
                                                        String content, String url, String publishTime, Boolean isNeedSign) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .submitActionContent(userId,partyMemberId,cateId,cateCode,name,icon,imgPaths,
                        mediaType,content,url,publishTime,isNeedSign)
                .map(new Func1<NotingResponse, NotingResponse>() {
                    @Override
                    public NotingResponse call(NotingResponse notingResponse) {
                        return notingResponse;
                    }
                })
                .compose(RxSchedulers.<NotingResponse>io_main());
    }

    @Override
    public Observable<UpLoadFileResponse> uploadFile(Map<String, RequestBody> parmas) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .uploadFile(parmas)
                .map(new Func1<UpLoadFileResponse, UpLoadFileResponse>() {
                    @Override
                    public UpLoadFileResponse call(UpLoadFileResponse upLoadFileResponse) {
                        return upLoadFileResponse;
                    }
                })
                .compose(RxSchedulers.<UpLoadFileResponse>io_main());
    }
}
