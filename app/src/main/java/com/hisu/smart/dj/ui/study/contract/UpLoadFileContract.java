package com.hisu.smart.dj.ui.study.contract;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
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
        Observable<BaseResponse> submitActionContent(Integer userId, Integer partyMemberId, Integer cateId,
                                                     String cateCode, String name, String icon,
                                                     Map<String, RequestBody> imgPaths, Integer mediaType,
                                                     String content, String url, String publishTime, Boolean isNeedSign);
    }

    interface View extends BaseView {
        void returnSubmitResponse(BaseResponse baseResponse);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void submitActionContentRequest(Integer userId, Integer partyMemberId, Integer cateId,
                                                        String cateCode, String name,String icon,
                                                        Map<String, RequestBody> imgPaths,Integer mediaType,
                                                        String content,String url,String publishTime,Boolean isNeedSign);
    }
}
