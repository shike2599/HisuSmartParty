package com.hisu.smart.dj.ui.study.contract;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.UpLoadFileResponse;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public interface UpLoadFileContract {
    interface Model extends BaseModel {
        Observable<NotingResponse> submitActionContent(Integer userId, Integer partyMemberId, Integer cateId,
                                                     String cateCode, String name, String icon,
                                                     String imgPaths, Integer mediaType,
                                                     String content, String url, String publishTime, Boolean isNeedSign);
        //上传图片
        Observable<UpLoadFileResponse> uploadFile(Map<String,RequestBody> parmas);
    }

    interface View extends BaseView {
        void returnSubmitResponse(NotingResponse notingResponse);
        //上传图片返回
        void returnUpLoadFile(UpLoadFileResponse upLoadFileResponse);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void submitActionContentRequest(Integer userId, Integer partyMemberId, Integer cateId,
                                                        String cateCode, String name,String icon,
                                                        String imgPaths,Integer mediaType,
                                                        String content,String url,String publishTime,Boolean isNeedSign);
        //上传图片请求
        public abstract void upLoadFileRequest(Map<String,RequestBody> parmas);
    }
}
